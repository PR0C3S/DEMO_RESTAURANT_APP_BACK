package com.dev.app_restaurant.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    @NotEmpty(message = "Por favor introduce un codigo.")
    @Size(message = "El nombre debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "El nombre debe contener maximo 15 caracteres.",max = 15)
    private String name;

    @NotNull(message = "Por favor introduce un porcentaje.")
    @Min(message = "El porcentaje minimo es 1.",value = 1)
    @Max(message = "El porcentaje maximo es 100.",value = 100)
    private Integer percentage;

    @NotNull(message = "Por favor introduce una fecha de expiracion.")
    private LocalDate expireAt;
}
