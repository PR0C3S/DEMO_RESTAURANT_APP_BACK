package com.dev.app_restaurant.service.Impl;


import com.dev.app_restaurant.dto.CategoryDTO;
import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.entity.Category;
import java.io.IOException;
import java.util.List;

public interface ICategoryService {
    public Category create(CategoryDTO typePlate) throws IOException;
    public Category updateById(CategoryDTO typePlate, Integer id) throws IOException;
    public void deleteById(Integer id);
    public Category findById(Integer id);
    public List<Category> categoryList(String searchBy, String orderBy);
    public Boolean existsByName(String name);
    public List<SelectDTO> findAllAsSelect();
    public Long count();
    public Boolean validateName(String name, Integer id);
}
