package com.dev.app_restaurant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotEmpty(message = "Nueva contraseña es requerida")
    @Size(message = "Nueva contraseña debe contener minimo 6 caracteres.",min = 6)
    private String newPassword;
    private String confirmNewPassword;
}
