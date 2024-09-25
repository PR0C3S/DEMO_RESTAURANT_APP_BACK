package com.dev.app_restaurant.controller;


import com.dev.app_restaurant.dto.OrderDTO;
import com.dev.app_restaurant.entity.Order;
import com.dev.app_restaurant.entity.User;
import com.dev.app_restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public List<Order> orderList(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                                 @RequestParam(defaultValue = "id-desc",name = "orderBy",required = false) String orderBy,
                                 @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                                 @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                                 @RequestParam(defaultValue = "ALL",name = "status",required = false) String status,
                                 @RequestParam(defaultValue = "ALL",name = "consume",required = false) String consume,
                                 @RequestParam(defaultValue = "ALL",name = "employee",required = false) String employee){
        return service.orderList(searchBy,orderBy,from,to,status,consume,employee);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable Integer id){
        return service.findById(id);
    }

    @GetMapping("/top5")
    public List<Order> findTop5Recents(){
        return service.findTop5Recents();
    }

    @GetMapping("/sumTotal")
    public BigDecimal sumTotal(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                               @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                               @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                               @RequestParam(defaultValue = "ALL",name = "status",required = false) String status,
                               @RequestParam(defaultValue = "ALL",name = "consume",required = false) String consume,
                               @RequestParam(defaultValue = "ALL",name = "employee",required = false) String employee){
        return service.sumTotal(searchBy,from,to,status,consume,employee);
    }

    @GetMapping("/count")
    public Long count(@RequestParam(defaultValue = "",name = "searchBy",required = false) String searchBy,
                      @RequestParam(defaultValue = "",name = "from",required = false) LocalDate from,
                      @RequestParam(defaultValue = "",name = "to",required = false) LocalDate to,
                      @RequestParam(defaultValue = "ALL",name = "status",required = false) String status,
                      @RequestParam(defaultValue = "ALL",name = "consume",required = false) String consume,
                      @RequestParam(defaultValue = "ALL",name = "employee",required = false) String employee){
        return service.count(searchBy,from,to,status,consume,employee);
    }



    @PostMapping
    public Order create(@Valid @RequestBody OrderDTO orderDTO, Authentication authentication){
        return service.create(orderDTO, (User) authentication.getPrincipal());
    }


    @PutMapping("/updateStatus/{id}")
    public Order updateStatusByID(@PathVariable Integer id,
                            @RequestParam String status){
        return service.updateStatusByID(id,status);
    }

}
