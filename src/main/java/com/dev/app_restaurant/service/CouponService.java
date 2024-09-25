package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.CouponDTO;
import com.dev.app_restaurant.entity.Coupon;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.CouponRepository;
import com.dev.app_restaurant.service.Impl.ICouponService;
import com.dev.app_restaurant.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    private CouponRepository repository;

    @Override
    public Coupon create(CouponDTO couponDTO) {
        String name = couponDTO.getName().toUpperCase();
        if (!validateName(name, null)) {
            throw new BadRequestException("Nombre en uso.");
        }

        LocalDateTime now = LocalDateTime.now();
        Coupon coupon = new Coupon(null, name, couponDTO.getPercentage(), now.toLocalDate(), now, couponDTO.getExpireAt());
        if (!coupon.getIsActive()) {
            throw new BadRequestException("Por favor introduce una fecha de expiracion valida.");
        }

        return repository.save(coupon);
    }

    @Override
    public Coupon updateStatusByID(Integer id, Boolean isActive) {
        Coupon coupon = findById(id);
        if (isActive) {
            //ACTIVATE COUPON
            LocalDate oneYear = LocalDate.now().plusYears(1);
            coupon.setExpireAt(oneYear);
        } else {
            //EXPIRED COUPON
            LocalDate yesterday = LocalDate.now().minusDays(1);
            coupon.setExpireAt(yesterday);
        }
        coupon.setUpdateAt(LocalDateTime.now());
        return repository.save(coupon);
    }

    @Override
    public Coupon findById(Integer id) {
        Coupon coupon = repository.findById(id).orElse(null);
        if (coupon == null) {
            throw new ResourceNotFoundException("EL ID no existe");
        }
        return coupon;
    }

    @Override
    public Coupon checkValid(String name) {
        Coupon coupon = repository.findByName(name);
        //TICKET EXPIRE OR NOT EXIST
        if (coupon == null || !coupon.getIsActive()) {
            return null;
        }
        return coupon;
    }


    @Override
    public Boolean existsByName(String code) {
        return repository.existsByName(code);
    }

    @Override
    public List<Coupon> couponList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status) {
        Sort sort = SortBy.getInstance().getSort(orderBy);
        String search1 = searchBy.isEmpty() ? null : searchBy;
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

        if (status.equalsIgnoreCase("true")) {
            return repository.findByFilterActive(from, to, search1, LocalDate.now(),sort);
        } else if (status.equalsIgnoreCase("false")) {
            return repository.findByFilterExpire(from, to, search1, LocalDate.now(),sort);
        } else {
            //it can be filter by active or expire it doesnt evaluate status
            return repository.findByFilterActive(from, to, search1, null,sort);
        }
    }


    @Override
    public Long count(String searchBy, LocalDate from, LocalDate to, String status) {
        String search1 = searchBy.isEmpty() ? null : searchBy;
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

        if (status.equalsIgnoreCase("true")) {
            return repository.countByFilterActive(from, to, search1, LocalDate.now());
        } else if (status.equalsIgnoreCase("false")) {
            return repository.countByFilterExpire(from, to, search1, LocalDate.now());
        } else {
            //it can be filter by active or expire it doesnt evaluate status
            return repository.countByFilterActive(from, to, search1, null);
        }

    }


    @Override
    public Boolean validateName(String name, Integer id) {
        Boolean exist = existsByName(name);
        if (exist) {
            if (id == null) {
                return false;
            } else {
                Coupon old = findById(id);
                return old.getName().equalsIgnoreCase(name);
            }
        }
        return true;
    }

}
