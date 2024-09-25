package com.dev.app_restaurant.service;


import com.dev.app_restaurant.dto.AuthResponse;
import com.dev.app_restaurant.dto.LoginDTO;
import com.dev.app_restaurant.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    public AuthResponse login(LoginDTO loginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        UserDetails user =  userService.findByUsername(loginDTO.getUsername());
        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }
}
