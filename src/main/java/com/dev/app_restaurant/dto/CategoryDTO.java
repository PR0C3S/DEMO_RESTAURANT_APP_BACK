package com.dev.app_restaurant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Por favor introduce un nombre.")
    @Size(message = "El nombre debe contener minimo 3 caracteres.",min = 3)
    @Size(message = "El nombre debe contener caracteres 25 caracteres.",max = 25)
    private String name;
    private MultipartFile image;
}
