package com.gear.attachment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum SensitiveStrategy {
    /**
     * 中文名，只显示第一个汉字，其他隐藏
     */
    CHINESE_NAME(s -> s.replaceAll("(\\S)\\S+", "$1**")),

    /**
     * 身份证号，保留前六位和后四位
     */
    ID_CARD(s -> s.replaceAll("(\\d{6})\\d+(\\d{4})", "$1********$2")),

    /**
     * 手机号，保留前三位和后四位
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d+(\\d{4})", "$1****$2")),

    /**
     * 银行卡号，保留前六位和后三位
     */
    BANK_CARD(s -> s.replaceAll("(\\d{6})\\d+(\\d{3})", "$1******$2")),

    /**
     * 地址，显示前六位，后面隐藏
     */
    ADDRESS(s -> s.replaceAll("(\\S{6})\\S+", "$1******")),


    /**
     * 汽车 VIN 码，保留前三位和后四位
     */
    CAR_VIN(s -> s.replaceAll("(\\w{3})\\w+(\\w{4})", "$1**********$2")),


    ;

    private final Function<String, String> desensitizer;
}