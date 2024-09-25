package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.RoleSelectDTO;
import com.dev.app_restaurant.entity.Role;
import com.dev.app_restaurant.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping
    public List<Role> roleList() {
        return service.roleList();
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/select")
    public List<RoleSelectDTO> findAllAsSelect() {
        return service.findAllAsSelect();
    }

    @GetMapping("/count")
    public Long count() {
        return service.count();
    }


}
