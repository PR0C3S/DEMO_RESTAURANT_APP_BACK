package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.entity.Category;
import com.dev.app_restaurant.entity.Plate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlateRepository extends JpaRepository<Plate,Integer> {
    Boolean existsByName(String name);
    Boolean existsByCategory(Category category);

    @Query("select p from Plate p where \n" +
            "(:from is null or :to is null or (p.createAt between :from and :to) )\n" +
            "and (:name is null or p.name LIKE %:name%)\n" +
            "and (:isActive is null or p.isActive = :isActive)\n" +
            "and (:category is null or p.category = :category)\n" )
    List<Plate> findByFilter(LocalDate from,LocalDate to,String name, Boolean isActive, Category category, Sort sort);

    @Query("select count(p.id) from Plate p where \n" +
            "(:from is null or :to is null or (p.createAt between :from and :to) )\n" +
            "and (:name is null or p.name LIKE %:name%)\n" +
            "and (:isActive is null or p.isActive = :isActive)\n" +
            "and (:category is null or p.category = :category)\n" )
    Long countByFilter(LocalDate from,LocalDate to,String name, Boolean isActive, Category category);
}
