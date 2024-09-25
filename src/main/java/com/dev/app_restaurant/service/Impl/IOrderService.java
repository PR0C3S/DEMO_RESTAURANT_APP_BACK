package com.dev.app_restaurant.service.Impl;

import com.dev.app_restaurant.dto.OrderDTO;
import com.dev.app_restaurant.entity.Order;
import com.dev.app_restaurant.entity.User;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IOrderService {
    public Order create(OrderDTO orderDTO, User loggin) throws Exception;
    public Order updateStatusByID(Integer id,String status);
    public Order findById(Integer id);
    public List<Order> orderList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status, String consume,String employee);
    public List<Order> findTop5Recents();
    public Long count(String searchBy, LocalDate from, LocalDate to, String status, String consume, String employee);
    public BigDecimal sumTotal(String searchBy, LocalDate from, LocalDate to, String status, String consume, String employee);
}
