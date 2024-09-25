package com.dev.app_restaurant.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String status; //Delivered,Cancelled, Waiting
    private String consume; //Local, Delivery
    private Integer coupon_id;

    private String clientName;
    private String clientPhone;
    private String orderTo;
    private String referenceOrderTo;
    private BigDecimal deliveryPrice;
    private Set<DetailOrderDTO> detailOrderList;
}
