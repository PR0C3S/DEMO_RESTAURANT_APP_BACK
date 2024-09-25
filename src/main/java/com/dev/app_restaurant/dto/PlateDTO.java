package com.dev.app_restaurant.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlateDTO {
    @NotEmpty(message = "Por favor introduce un nombre.")
    @Size(message = "El nombre debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "El nombre debe contener caracteres 50 caracteres.",max = 50)
    private String name;

    @NotNull(message = "Por favor introduce un precio.")
    @Min(message = "Por favor introduce un precio valido.",value = 1)
    private BigDecimal price;

    @NotNull(message = "Por favor selecciona una categoria valida.")
    @Min(message = "Por favor selecciona una categoria valida.",value = 1)
    private Integer category_id;

    private Boolean isActive;  //active,inactive
}
