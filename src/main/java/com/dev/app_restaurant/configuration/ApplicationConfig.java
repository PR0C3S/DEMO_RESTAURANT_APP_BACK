package com.dev.app_restaurant.configuration;


import com.dev.app_restaurant.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    AuthService authService() {
        return new AuthService();
    }

    @Bean
    CategoryService categoryService() {
        return new CategoryService();
    }

    @Bean
    CouponService couponService() {
        return new CouponService();
    }

    @Bean
    OrderService orderService() {
        return new OrderService();
    }

    @Bean
    PlateService plateService() {
        return new PlateService();
    }

    @Bean
    RoleService roleService() {
        return new RoleService();
    }
    @Bean
    SettingService settingService() {
        return new SettingService();
    }
    @Bean
    UserService userService() {
        return new UserService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailService() {
        return username -> userService().findByUsername(username);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
