# gear-sensitive-spring-boot-starter
脱敏组件
本组件基于 Jackson 实现，暂不支持其他 JSON 框架
测试类 执行命令 mvn clean test  查看测试类结果

原理：当 Spring MVC 处理 Controller 的返回值并准备转成 JSON 时，它会调用 Jackson 库。

逻辑：Jackson 在扫描 DTO 字段时，发现字段上有 @Sensitive。由于它标了 @JacksonAnnotationsInside，Jackson 会自动解析出里面嵌套的 @JsonSerialize(using = SensitiveSerializer.class)。

结果：Jackson 会自动实例化 SensitiveSerializer 类（注意：这是 Jackson 内部实例化的，不一定需要是 Spring Bean）
