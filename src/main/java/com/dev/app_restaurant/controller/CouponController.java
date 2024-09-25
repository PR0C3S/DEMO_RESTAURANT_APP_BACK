package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.CouponDTO;
import com.dev.app_restaurant.entity.Coupon;
import com.dev.app_restaurant.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/coupons")
public class CouponController {

    @Autowired
    private CouponService service;

    @GetMapping
    public List<Coupon> couponList(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                                   @RequestParam(defaultValue = "id-desc",name = "orderBy",required = false) String orderBy,
                                   @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                                   @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                                   @RequestParam(defaultValue = "ALL",name = "status",required = false) String status){
        return service.couponList(searchBy,orderBy,from,to,status);
    }

    @GetMapping("/{id}")
    public Coupon findById(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/checkValid/{name}")
    public Coupon checkValid(@PathVariable String name) {
        return service.checkValid(name);
    }

    @GetMapping("/validateName")
    public Boolean validateName(@RequestParam String name, @RequestParam(required = false,defaultValue = "") Integer id){
        return service.validateName(name.trim(),id);
    }

    @GetMapping("/count")
    public Long count(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                      @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                      @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                      @RequestParam(defaultValue = "ALL",name = "status",required = false) String status){
        return service.count(searchBy,from,to,status);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Coupon create(@Valid @RequestBody CouponDTO add) {
        return service.create(add);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateStatus/{id}")
    public Coupon updateStatusByID(@PathVariable Integer id,
                                  @RequestParam Boolean status){
        return service.updateStatusByID(id,status);
    }
}
