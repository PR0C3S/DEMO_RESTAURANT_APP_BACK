package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.DetailOrderDTO;
import com.dev.app_restaurant.dto.OrderDTO;
import com.dev.app_restaurant.entity.*;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.OrderRepository;
import com.dev.app_restaurant.service.Impl.IOrderService;
import com.dev.app_restaurant.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private PlateService plateService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private SettingService settingServicee;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> findTop5Recents() {
        return repository.findFirst5ByOrderByIdDesc();
    }

    @Override
    public Long count(String searchBy, LocalDate from, LocalDate to, String status, String consume, String employee) {
        boolean containDate = from != null && to != null;
        if (from == null && to != null) {
            throw new BadRequestException("La fecha 'Desde' es requerida si La fecha 'Hasta' está presente.");
        }
        if (from != null && to == null) {
            throw new BadRequestException("La fecha 'Hasta' es requerida si la fecha 'Desde' está presente.");
        }
        if (containDate && to.isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
        }
        if (containDate && LocalDate.now().isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior al dia de Hoy.");
        }
        if (containDate && LocalDate.now().isBefore(to)) {
            throw new BadRequestException("La fecha 'Hasta' no puede ser posterior al dia de Hoy.");
        }

        String search1 = searchBy.isEmpty() ? null : searchBy;
        String status1 = status.equalsIgnoreCase("ALL") ? null : status;
        String consume1 = consume.equalsIgnoreCase("ALL") ? null : consume;
        User employee1 = employee.equalsIgnoreCase("ALL") ? null : userService.findById(Integer.valueOf(employee));
        return repository.countByFilter(from, to, search1, status1, consume1,employee1);
    }


    @Override
    public BigDecimal sumTotal(String searchBy, LocalDate from, LocalDate to, String status, String consume, String employee) {
        boolean containDate = from != null && to != null;
        if (from == null && to != null) {
            throw new BadRequestException("La fecha 'Desde' es requerida si La fecha 'Hasta' está presente.");
        }
        if (from != null && to == null) {
            throw new BadRequestException("La fecha 'Hasta' es requerida si la fecha 'Desde' está presente.");
        }
        if (containDate && to.isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
        }
        if (containDate && LocalDate.now().isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior al dia de Hoy.");
        }
        if (containDate && LocalDate.now().isBefore(to)) {
            throw new BadRequestException("La fecha 'Hasta' no puede ser posterior al dia de Hoy.");
        }

        String search1 = searchBy.isEmpty() ? null : searchBy;
        String status1 = status.equalsIgnoreCase("ALL") ? null : status;
        String consume1 = consume.equalsIgnoreCase("ALL") ? null : consume;
        User employee1 = employee.equalsIgnoreCase("ALL") ? null : userService.findById(Integer.valueOf(employee));
        return repository.sumByFilter(from, to, search1, status1, consume1,employee1);
    }


    public Order create(OrderDTO orderDTO, User loggin) {

        Setting settings = settingServicee.get();
        Order order = new Order();
        for (DetailOrderDTO detailOrderDTO : orderDTO.getDetailOrderList()) {
            Plate plate = plateService.findById(detailOrderDTO.getId());
            order.AddDetailOrder(new DetailOrder(null, null, plate, plate.getName(), detailOrderDTO.getQuantity(), plate.getPrice()));
        }

        String client = orderDTO.getClientName().isEmpty() ? "No especificado" : orderDTO.getClientName();
        LocalDateTime now = LocalDateTime.now();
        order.setCreateAt(now.toLocalDate());
        order.setUpdateAt(now);
        order.setConsume(orderDTO.getConsume().toUpperCase());
        order.setClientName(client);
        order.setStatus(orderDTO.getStatus().toUpperCase());

        BigDecimal subTotal = order.SubTotal();
        float subtotalAsFloat = subTotal.floatValue();
        BigDecimal deliveryPrice = BigDecimal.ZERO;
        BigDecimal taxes = BigDecimal.ZERO;
        BigDecimal itbis = BigDecimal.valueOf(settings.getITBISAsFloat());

        if (orderDTO.getConsume().equalsIgnoreCase("DELIVERY")) {
            order.setOrderTo(orderDTO.getOrderTo());
            order.setClientPhone(orderDTO.getClientPhone());
            order.setReferenceOrderTo(orderDTO.getReferenceOrderTo());
            deliveryPrice = orderDTO.getDeliveryPrice();
        } else {
            taxes = BigDecimal.valueOf(settings.getTaxesAsFloat());
        }

        BigDecimal discount = BigDecimal.ZERO;
        Coupon coupon = null;

        //Set Coupon
        if (orderDTO.getCoupon_id() != null) {
            coupon = couponService.findById(orderDTO.getCoupon_id());
            //APPLY COUPON IF IS VALID
            if (coupon.getIsActive()) {
                discount = BigDecimal.valueOf(subtotalAsFloat * coupon.getPercentageAsFloat());
            } else {
                coupon = null;
            }
        }

        BigDecimal total = subTotal.add(itbis).add(deliveryPrice).add(taxes).subtract(discount);
        Invoice invoice = new Invoice(null, now, loggin, coupon, deliveryPrice, subTotal, settings.getItbisPercentage(), itbis, settings.getTaxesPercentage(), taxes, discount, total, order.QuantityItem());
        order.setInvoice(invoice);
        return repository.save(order);
    }

    @Override
    public Order updateStatusByID(Integer id, String status) {
        Order order = findById(id);
        order.setUpdateAt(LocalDateTime.now());
        order.setStatus(status);
        return repository.save(order);
    }


    @Override
    public Order findById(Integer id) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("EL ID no existe");
        }
        return order;
    }

    @Override
    public List<Order> orderList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status, String consume, String employee) {
        Sort sort = SortBy.getInstance().getSort(orderBy);

        boolean containDate = from != null && to != null;
        if (from == null && to != null) {
            throw new BadRequestException("La fecha 'Desde' es requerida si La fecha 'Hasta' está presente.");
        }
        if (from != null && to == null) {
            throw new BadRequestException("La fecha 'Hasta' es requerida si la fecha 'Desde' está presente.");
        }
        if (containDate && to.isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
        }
        if (containDate && LocalDate.now().isBefore(from)) {
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior al dia de Hoy.");
        }
        if (containDate && LocalDate.now().isBefore(to)) {
            throw new BadRequestException("La fecha 'Hasta' no puede ser posterior al dia de Hoy.");
        }

        String search1 = searchBy.isEmpty() ? null : searchBy;
        String status1 = status.equalsIgnoreCase("ALL") ? null : status;
        String consume1 = consume.equalsIgnoreCase("ALL") ? null : consume;
        User employee1 = employee.equalsIgnoreCase("ALL") ? null : userService.findById(Integer.valueOf(employee));

        return repository.findByFilter(from, to, search1, status1, consume1,employee1, sort);
    }

}
