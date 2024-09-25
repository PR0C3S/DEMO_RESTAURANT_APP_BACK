package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.entity.Coupon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Coupon findByName(String name);
    Boolean existsByName(String name);

    @Query("select count(c.id) from Coupon c where \n" +
            "(:from is null or :to is null or (c.createAt between :from and :to) )\n" +
            "and (:name is null or c.name LIKE %:name%)\n" +
            "and (:today is null or c.expireAt >= :today)\n" )
    Long countByFilterActive(LocalDate from,LocalDate to,String name,LocalDate today);

    @Query("select count(c.id) from Coupon c where \n" +
            "(:from is null or :to is null or (c.createAt between :from and :to) )\n" +
            "and (:name is null or c.name LIKE %:name%)\n" +
            "and (:today is null or c.expireAt < :today)\n" )
    Long countByFilterExpire(LocalDate from,LocalDate to,String name,LocalDate today);

    @Query("select c from Coupon c where \n" +
            "(:from is null or :to is null or (c.createAt between :from and :to) )\n" +
            "and (:name is null or c.name LIKE %:name%)\n" +
            "and (:today is null or c.expireAt >= :today)\n" )
    List<Coupon> findByFilterActive(LocalDate from,LocalDate to,String name,LocalDate today,Sort sort);

    @Query("select c from Coupon c where \n" +
            "(:from is null or :to is null or (c.createAt between :from and :to) )\n" +
            "and (:name is null or c.name LIKE %:name%)\n" +
            "and (:today is null or c.expireAt < :today)\n" )
    List<Coupon> findByFilterExpire(LocalDate from,LocalDate to,String name,LocalDate today,Sort sort);


}
