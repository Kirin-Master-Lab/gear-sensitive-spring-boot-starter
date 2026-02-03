package com.gear.attachment.config.dto;

import com.gear.attachment.annotation.Sensitive;
import com.gear.attachment.enums.SensitiveStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveTestDTO {

    @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
    private String realName;

    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;


    /**
     * vin
     */
    @Sensitive(strategy = SensitiveStrategy.CAR_VIN)
    private String vin;

    private String normalField; // 不脱敏字段
}
