package com.dev.app_restaurant.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Order_T")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade=CascadeType.ALL,orphanRemoval = true)
    private Invoice invoice;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "order")
    private List<DetailOrder> detailOrderList= new ArrayList<>();

    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate createAt;

    @Column(columnDefinition = "TIMESTAMP",nullable = false)
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private String status; //Delivered,Cancelled, Waiting

    @Column(nullable = false)
    private String consume; //Local, Delivery

    @Column
    private String clientName;

    @Column
    private String clientPhone;

    @Column
    private String orderTo;

    @Column
    private String referenceOrderTo;



    public void AddDetailOrder(DetailOrder detailOrder){
        detailOrder.setOrder(this);
        detailOrderList.add(detailOrder);
    }

    public void RemoveDetailOrder(DetailOrder detailOrder){
        detailOrderList.remove(detailOrder);
        detailOrder.setOrder(null);
    }

    public BigDecimal SubTotal(){
        BigDecimal totalPrice= BigDecimal.ZERO;
        for (DetailOrder order: detailOrderList){
            totalPrice = totalPrice.add(order.calculateTotal());
        }
        return totalPrice;
    }

    public Integer QuantityItem(){
        Integer quantity = 0;
        for(DetailOrder detailOrder: detailOrderList){
            quantity+=detailOrder.getQuantity();
        }

        return quantity;
    }

}
