package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByName(String name);
}
