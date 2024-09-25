package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.PlateDTO;
import com.dev.app_restaurant.entity.Plate;
import com.dev.app_restaurant.service.PlateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/plates")
public class PlateController {

    @Autowired
    private PlateService service;

    @GetMapping
    public List<Plate> plateList(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                                 @RequestParam(defaultValue = "id-desc",name = "orderBy",required = false) String orderBy,
                                 @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                                 @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                                 @RequestParam(defaultValue = "ALL",name = "status",required = false) String status,
                                 @RequestParam(defaultValue = "ALL",name = "category",required = false) String category){
        return service.plateList(searchBy,orderBy,from,to,status,category);
    }

    @GetMapping("/count")
    public Long count(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                              @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                              @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                              @RequestParam(defaultValue = "ALL",name = "status",required = false) String status,
                              @RequestParam(defaultValue = "ALL",name = "category",required = false) String category){
        return service.count(searchBy,from,to,status,category);
    }

    @GetMapping("/{id}")
    public Plate findById(@PathVariable Integer id){
        return service.findById(id);
    }


    @GetMapping("/category/{categoryId}")
    public List<Plate> findAllByCategory(@PathVariable Integer categoryId){
        return service.findAllByCategory(categoryId);
    }

    @GetMapping("/validateName")
    public Boolean validateName(@RequestParam String name, @RequestParam(required = false,defaultValue = "") Integer id){
        return service.validateName(name.trim(),id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Plate create(@Valid @RequestBody PlateDTO plateDTO){
        return service.create(plateDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Plate updateById(@Valid @RequestBody PlateDTO plateDTO, @PathVariable Integer id){
        return service.updateById(plateDTO, id);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateStatus/{id}")
    public Plate updateStatusByID(@PathVariable Integer id,
                                 @RequestParam Boolean status){
        return service.updateStatusByID(id,status);
    }


}
