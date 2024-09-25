package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.SettingDTO;
import com.dev.app_restaurant.entity.Setting;
import com.dev.app_restaurant.service.SettingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/settings")
public class SettingController {

    @Autowired
    private SettingService service;

    @GetMapping("/")
    public Setting getConfiguration(){
        return service.get();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public Setting updateConfiguration(@Valid @ModelAttribute SettingDTO add) throws IOException {
        return service.update(add);
    }

}
