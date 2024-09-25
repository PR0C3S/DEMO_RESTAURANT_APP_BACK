package com.dev.app_restaurant.service.Impl;

import com.dev.app_restaurant.dto.CouponDTO;
import com.dev.app_restaurant.entity.Coupon;
import java.time.LocalDate;
import java.util.List;

public interface ICouponService {
    public Coupon create(CouponDTO couponDTO);
    public Coupon updateStatusByID(Integer id, Boolean status);
    public Coupon findById(Integer id);
    public Coupon checkValid(String name);
    public Boolean existsByName(String name);
    public List<Coupon> couponList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status);
    public Long count(String searchBy, LocalDate from, LocalDate to, String status);
    public Boolean validateName(String name, Integer id);
}
