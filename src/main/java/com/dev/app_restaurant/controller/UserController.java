package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.ChangePasswordDTO;
import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.dto.UpdateMeDTO;
import com.dev.app_restaurant.dto.UserDTO;
import com.dev.app_restaurant.entity.User;
import com.dev.app_restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<User> userList(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                               @RequestParam(defaultValue = "id-desc",name = "orderBy",required = false) String orderBy,
                               @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                               @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                               @RequestParam(defaultValue = "ALL",name = "status",required = false) String status){
        return service.userList(searchBy,orderBy,from,to,status);
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return service.findById(id);
    }


    @GetMapping("/validateUsername")
    public Boolean validateUsername(@RequestParam String username, @RequestParam(required = false,defaultValue = "") Integer id){
        return service.validateUsername(username.trim(),id);
    }

    @GetMapping("/validatePhone")
    public Boolean validatePhone(@RequestParam String phone, @RequestParam(required = false,defaultValue = "") Integer id){
        return service.validatePhone(phone.trim(),id);
    }

    @GetMapping("/count")
    public Long count(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                      @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                      @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                      @RequestParam(defaultValue = "ALL",name = "status",required = false) String status){
        return service.count(searchBy,from,to,status);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/select")
    public List<SelectDTO> findAllAsSelect(){
        return service.findAllAsSelect();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public User create(@Valid @RequestBody UserDTO add) {
        return service.create(add);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public User updateById(@Valid @RequestBody UserDTO add, @PathVariable Integer id,Authentication authentication) {
        return service.updateById(add,id,(User) authentication.getPrincipal());
    }

    @PutMapping("/updateMe")
    public User updateLoggin(@Valid @RequestBody UpdateMeDTO add, Authentication authentication) {
        return service.updateLoggin(add,(User) authentication.getPrincipal());
    }

    @PutMapping("/changePassword")
    public User changePasswordLoggin(@Valid @RequestBody ChangePasswordDTO add, Authentication authentication) {
        return service.changePasswordLoggin(add,(User) authentication.getPrincipal());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateStatus/{id}")
    public User updateStatusById(@PathVariable Integer id,@RequestParam Boolean status ) {
        return service.updateStatusById(id,status);
    }

}
