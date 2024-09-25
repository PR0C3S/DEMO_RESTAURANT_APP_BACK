package com.dev.app_restaurant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer role_id;
    @NotEmpty(message = "Usuario es requerido.")
    @Size(message = "Usuario debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "Usuario debe contener maximo 25 caracteres.",max = 25)
    private String username;

    @NotEmpty(message = "Nombres es requerido.")
    @Size(message = "Nombres debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "Nombres debe contener maximo 25 caracteres.",max = 25)
    private String firstName;

    @NotEmpty(message = "Apellidos es requerido.")
    @Size(message = "Apellidos debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "Apellidos debe contener maximo 25 caracteres.",max = 25)
    private String lastName;

    @NotEmpty(message = "Telefono es requerido.")
    private String phone;

    private String password;
    private String confirmPassword;

    private Boolean isActive;
}
