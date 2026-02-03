package com.gear.attachment.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import com.gear.attachment.annotation.Sensitive;
import com.gear.attachment.enums.SensitiveStrategy;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 终极性能优化版：支持实例缓存的脱敏序列化器
 */
@NoArgsConstructor
public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveStrategy strategy;

    /**
     * 策略实例缓存：针对每种策略只创建一个序列化器单例
     */
    private static final Map<SensitiveStrategy, SensitiveSerializer> CACHE = new ConcurrentHashMap<>();

    private SensitiveSerializer(SensitiveStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value) || Objects.isNull(strategy)) {
            gen.writeString(value);
        } else {
            gen.writeString(strategy.getDesensitizer().apply(value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property == null) {
            return prov.findNullValueSerializer(null);
        }

        Sensitive annotation = property.getAnnotation(Sensitive.class);
        if (annotation != null) {
            SensitiveStrategy strategy = annotation.strategy();
            // 从缓存获取，如果没有则创建并放入缓存 (ComputeIfAbsent 保证线程安全)
            return CACHE.computeIfAbsent(strategy, SensitiveSerializer::new);
        }

        return prov.findValueSerializer(property.getType(), property);
    }
}
