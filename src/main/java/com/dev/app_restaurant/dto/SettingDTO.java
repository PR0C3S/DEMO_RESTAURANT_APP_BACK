package com.dev.app_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingDTO {
    private String name;
    private String rnc;
    private String principalPhone;
    private String secondaryPhone;
    private String location;
    private String logo;
    private Integer itbisPercentage;
    private Integer taxesPercentage;
    private BigDecimal deliveryPrice;
    private MultipartFile image;
}
