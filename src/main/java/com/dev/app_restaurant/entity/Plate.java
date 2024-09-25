package com.dev.app_restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate createAt;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private LocalDateTime updateAt;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

}
