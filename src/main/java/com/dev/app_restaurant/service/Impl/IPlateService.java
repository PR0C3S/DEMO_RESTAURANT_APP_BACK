package com.dev.app_restaurant.service.Impl;

import com.dev.app_restaurant.dto.PlateDTO;
import com.dev.app_restaurant.entity.Plate;
import java.time.LocalDate;
import java.util.List;

public interface IPlateService {
    public Plate create(PlateDTO plate);
    public Plate updateById(PlateDTO plate, Integer id);
    public Plate findById(Integer id);
    public List<Plate> plateList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status, String category);
    public List<Plate> findAllByCategory(Integer categoryId);
    public Long count(String searchBy, LocalDate from, LocalDate to, String status, String category);
    public Boolean existsByName(String name);
    public Boolean validateName(String name, Integer id);
    public Plate updateStatusByID(Integer id, Boolean status);
}
