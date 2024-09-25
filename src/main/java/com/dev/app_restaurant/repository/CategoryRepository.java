package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Boolean existsByName(String name);
    List<Category> findByNameContains(String name, Sort sort);

    @Query(value = "SELECT new com.dev.app_restaurant.dto.SelectDTO(name,id) from Category")
    List<SelectDTO> findAllAsSelect();
}
