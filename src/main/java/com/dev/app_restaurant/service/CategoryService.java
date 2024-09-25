package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.CategoryDTO;
import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.entity.Category;
import com.dev.app_restaurant.entity.Image;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.CategoryRepository;
import com.dev.app_restaurant.repository.PlateRepository;
import com.dev.app_restaurant.service.Impl.ICategoryService;
import com.dev.app_restaurant.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public Category create(CategoryDTO categoryDTO) throws IOException {
        String name = categoryDTO.getName().trim();
        if (!validateName(name,null)) {
            throw new BadRequestException("Nombre en uso.");
        }

        if(categoryDTO.getImage() == null){
            throw new BadRequestException("Imagen es requerida.");
        }

        Image image = imageService.create(categoryDTO.getImage());
        Category category = new Category(null, name,image);
        return repository.save(category);
    }

    @Override
    public Category updateById(CategoryDTO categoryDTO, Integer id) throws IOException {
        Category old = findById(id);
        String name = categoryDTO.getName().trim();

        if (!validateName(name,id)) {
            throw new BadRequestException("Nombre en uso.");
        }

        if(categoryDTO.getImage() != null){
            imageService.deleteByID(old.getImage().getId());
            Image image = imageService.create(categoryDTO.getImage());
            old.setImage(image);
        }
        old.setName(name);
        return repository.save(old);
    }

    @Override
    public void deleteById(Integer id) {
        Category category = repository.findById(id).orElse(null);
        if (category == null) {
            throw new ResourceNotFoundException("EL ID no existe");
        }

        if(plateRepository.existsByCategory(category)){
            throw new BadRequestException("La categoria tiene platos creados no se puede eliminar");
        }
        imageService.deleteByID(category.getImage().getId());
        repository.delete(category);

    }

    @Override
    public Category findById(Integer id) {
        Category category = repository.findById(id).orElse(null);
        if (category == null) {
            throw new ResourceNotFoundException("EL ID no existe");
        }
        return category;
    }

    @Override
    public List<Category> categoryList(String searchBy, String orderBy) {
        Sort sort = SortBy.getInstance().getSort(orderBy);
        if(!searchBy.isEmpty()){
            return repository.findByNameContains(searchBy,sort);
        }
        return repository.findAll(sort);
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsByName(name);
    }


    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean validateName(String name, Integer id) {
        Boolean exist = existsByName(name);
        if (exist) {
            if (id == null) {
                return false;
            } else {
                Category old = findById(id);
                return old.getName().equalsIgnoreCase(name);
            }
        }
        return true;
    }

    @Override
    public List<SelectDTO> findAllAsSelect() {
        return repository.findAllAsSelect();
    }
}
