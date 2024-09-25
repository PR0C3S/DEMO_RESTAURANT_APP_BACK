package com.dev.app_restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSelectDTO {
    private String label;
    private Integer value;
}
