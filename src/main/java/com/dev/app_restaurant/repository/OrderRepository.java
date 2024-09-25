package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.entity.Order;
import com.dev.app_restaurant.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findFirst5ByOrderByIdDesc();

    @Query("select IFNULL(sum(i.total), 0) from Invoice i " +
            "INNER JOIN Order o ON  i.id=o.invoice.id \n" +
            "where \n" +
            "(:from is null or :to is null or (o.createAt between :from and :to) )\n" +
            "and (:clientName is null or o.clientName LIKE %:clientName%)\n" +
            "and (:status is null or o.status = :status)\n" +
            "and (:consume is null or o.consume = :consume)\n" +
            "and (:employee is null or i.employee = :employee)\n")
    BigDecimal sumByFilter(LocalDate from, LocalDate to, String clientName, String status, String consume, User employee);


    @Query("select o from Order o " +
                  "INNER JOIN Invoice i ON  o.invoice.id = i.id \n" +
                  "where \n" +
                  "(:from is null or :to is null or (o.createAt between :from and :to) )\n" +
                  "and (:clientName is null or o.clientName LIKE %:clientName%)\n" +
                  "and (:status is null or o.status = :status)\n" +
                  "and (:consume is null or o.consume = :consume)\n" +
                  "and (:employee is null or i.employee = :employee)\n")
    List<Order> findByFilter(LocalDate from, LocalDate to, String clientName, String status, String consume, User employee, Sort sort);

    @Query("select count(o.id) from Order o " +
            "INNER JOIN Invoice i ON  o.invoice.id = i.id \n" +
            "where \n" +
            "(:from is null or :to is null or (o.createAt between :from and :to) )\n" +
            "and (:clientName is null or o.clientName LIKE %:clientName%)\n" +
            "and (:status is null or o.status = :status)\n" +
            "and (:consume is null or o.consume = :consume)\n" +
            "and (:employee is null or i.employee = :employee)\n")
    Long countByFilter(LocalDate from, LocalDate to, String clientName, String status, String consume, User employee);


}
