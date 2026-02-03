package com.gear.sensitive.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gear.sensitive.config.dto.SensitiveTestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;

@JsonTest // 专门用于测试 JSON 序列化的注解，启动速度极快
@Import(SensitiveAutoConfiguration.class)
public class SensitiveSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSensitive() throws Exception {
        // 1. 构造原始数据
        SensitiveTestDTO dto = SensitiveTestDTO.builder()
                .realName("张三丰")
                .phone("13812345678")
                .idCard("110101199001011234")
                .normalField("保持原样")
                .vin("A1SD2333334444423")
                .build();

        // 2. 执行序列化
        String json = objectMapper.writeValueAsString(dto);
        System.out.println("脱敏后的JSON: " + json);

        // 3. 断言验证
        // 姓名：张**
        Assertions.assertTrue(json.contains("张**"));
        // 手机：138****5678
        Assertions.assertTrue(json.contains("138****5678"));
        // 身份证：110101********1234
        Assertions.assertTrue(json.contains("110101********1234"));
        // 普通字段
        Assertions.assertTrue(json.contains("保持原样"));
    }

    @Test
    public void testNullValue() throws Exception {
        // 测试空值情况，确保不会触发 NPE
        SensitiveTestDTO dto = new SensitiveTestDTO();
        String json = objectMapper.writeValueAsString(dto);

        System.out.println("空对象序列化: " + json);
        Assertions.assertTrue(json.contains("\"realName\":null"));
    }
}