package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.AuthResponse;
import com.dev.app_restaurant.dto.LoginDTO;
import com.dev.app_restaurant.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();



    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginDTO loginDTO){
        return authService.login(loginDTO);
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
    }
}
