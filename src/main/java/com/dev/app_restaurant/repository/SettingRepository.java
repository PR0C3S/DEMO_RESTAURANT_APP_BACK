package com.dev.app_restaurant.repository;


import com.dev.app_restaurant.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
}
