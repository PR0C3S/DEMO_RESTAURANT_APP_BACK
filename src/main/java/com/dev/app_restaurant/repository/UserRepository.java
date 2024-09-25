package com.dev.app_restaurant.repository;


import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String name);
    Boolean existsByPhone(String phone);

    @Query(value = "SELECT new com.dev.app_restaurant.dto.SelectDTO(CONCAT(u.firstName, ' ', u.lastName),u.id) from User u")
    List<SelectDTO> findAllAsSelect();


    @Query("select count(u.id) from User u where \n" +
            "(:from is null or :to is null or (u.createAt between :from and :to) )\n" +
            "and (:name is null or (u.firstName like %:name% or u.lastName like %:name%))\n" +
            "and (:isActive is null or u.isActive = :isActive)\n")
    Long countByFilter(LocalDate from,LocalDate to,String name,Boolean isActive);


    @Query("select u from User u where \n" +
            "(:from is null or :to is null or (u.createAt between :from and :to) )\n" +
            "and (:name is null or (u.firstName like %:name% or u.lastName like %:name%))\n" +
            "and (:isActive is null or u.isActive = :isActive)\n")
    List<User>findByFilter (LocalDate from,LocalDate to,String name,Boolean isActive,Sort sort);
}
