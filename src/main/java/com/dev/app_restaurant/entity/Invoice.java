package com.dev.app_restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private LocalDateTime createAt;

    @ManyToOne
    private User employee;

    @ManyToOne
    private Coupon coupon;

    @Column(nullable = false)
    private BigDecimal deliveryPrice;

    @Column(nullable = false)
    private BigDecimal subTotal;


    @Column(nullable = false)
    private Integer itbisPercentage;

    @Column(nullable = false)
    private BigDecimal itbis;


    @Column(nullable = false)
    private Integer taxesPercentage;

    @Column(nullable = false)
    private BigDecimal taxes;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private Integer quantityItems;

    private Float getItbisPercetage(){
        return  itbisPercentage /100f;
    }
    private Float getTaxesPercetage(){
        return taxesPercentage /100f;
    }
}
