package com.dev.app_restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer percentage;

    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate createAt;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private LocalDateTime updateAt;
    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate expireAt;

    public Boolean getIsActive(){
        return !expireAt.isBefore(LocalDate.now());
    }
    public Float getPercentageAsFloat(){
        return percentage/100f;
    }
}
