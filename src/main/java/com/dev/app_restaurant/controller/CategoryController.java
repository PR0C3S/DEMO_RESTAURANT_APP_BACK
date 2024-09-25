package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.CategoryDTO;
import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.entity.Category;
import com.dev.app_restaurant.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> categoryList(
            @RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
            @RequestParam(defaultValue = "id-desc",name = "orderBy",required = false) String orderBy
            ){
        return service.categoryList(searchBy,orderBy);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/select")
    public List<SelectDTO> findAllAsSelect(){
        return service.findAllAsSelect();
    }

    @GetMapping("/validateName")
    public Boolean validateName(@RequestParam String name, @RequestParam(required = false,defaultValue = "") Integer id){
        return service.validateName(name.trim(),id);
    }

    @GetMapping("/count")
    public Long count(){
        return service.count();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Category create(@Valid @ModelAttribute CategoryDTO add) throws IOException {
        return service.create(add);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Category updateById(@Valid @ModelAttribute CategoryDTO add, @PathVariable Integer id) throws IOException {
        return service.updateById(add,id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        service.deleteById(id);
    }


}
