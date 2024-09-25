package com.dev.app_restaurant.repository;


import com.dev.app_restaurant.entity.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder,Integer> {
}
