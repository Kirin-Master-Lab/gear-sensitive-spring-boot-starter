package com.gear.attachment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 脱敏组件自动配置
 * 条件 1：类路径下必须有 ObjectMapper (Jackson 核心类)
 */
@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)
public class SensitiveAutoConfiguration {

    /**
     * 这里其实可以不写 @Bean，因为 Jackson 是通过注解反射调用的。
     * 但我们可以注册一个标记性的 Bean，或者用于未来扩展全局脱敏开关。
     */
    @Bean
    @ConditionalOnMissingBean
    public SensitiveSerializer sensitiveSerializer() {
        return new SensitiveSerializer();
    }
}