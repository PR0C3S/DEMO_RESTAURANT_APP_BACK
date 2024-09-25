package com.dev.app_restaurant.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderDTO {
    @Min(message = "Por favor introduce el plato.",value = 0)
    private Integer id;

    @Min(message = "Por favor introduce una cantidad valida.",value = 0)
    private Integer quantity;

}
