package com.dev.app_restaurant.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String rnc;
    private String principalPhone;
    private String secondaryPhone;
    private String location;
    private Integer itbisPercentage;
    private Integer taxesPercentage;
    private BigDecimal deliveryPrice;


    public Float getITBISAsFloat(){
        return itbisPercentage/100f;
    }

    public Float getTaxesAsFloat(){
        return taxesPercentage/100f;
    }

    //Agregar Imagen
    @OneToOne(cascade=CascadeType.ALL,orphanRemoval = true)
    private Image logo;
}
