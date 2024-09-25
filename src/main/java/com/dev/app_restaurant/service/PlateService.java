package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.PlateDTO;
import com.dev.app_restaurant.entity.Category;
import com.dev.app_restaurant.entity.Plate;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.PlateRepository;
import com.dev.app_restaurant.service.Impl.IPlateService;
import com.dev.app_restaurant.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlateService implements IPlateService {

    @Autowired
    private PlateRepository repository;

    @Autowired
    private CategoryService categoryService;


    @Override
    public Plate create(PlateDTO plateDTO) {
        String name = plateDTO.getName().trim();
        if (!validateName(name, null)) {
            throw new BadRequestException("Nombre en uso.");
        }

        Category category = categoryService.findById(plateDTO.getCategory_id());
        LocalDateTime now = LocalDateTime.now();

        Plate add = new Plate(null, now.toLocalDate(), now, name, plateDTO.getPrice(), plateDTO.getIsActive(), category);
        return repository.save(add);
    }

    @Override
    public Plate updateById(PlateDTO plateDTO, Integer id) {
        Plate old = findById(id);
        String name = plateDTO.getName().trim();

        if (!validateName(name, id)) {
            throw new BadRequestException("Nombre en uso.");
        }

        Category category = categoryService.findById(plateDTO.getCategory_id());
        old.setUpdateAt(LocalDateTime.now());
        old.setName(name);
        old.setPrice(plateDTO.getPrice());
        old.setCategory(category);
        old.setIsActive(plateDTO.getIsActive());
        return repository.save(old);
    }


    @Override
    public Plate findById(Integer id) {
        Plate plate = repository.findById(id).orElse(null);
        if (plate == null) {
            throw new ResourceNotFoundException("EL ID no existe");
        }
        return plate;
    }


    @Override
    public List<Plate> plateList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status, String category) {
        Sort sort = SortBy.getInstance().getSort(orderBy);

        boolean containDate = from!=null && to!=null;
        if(from==null&& to!=null){
            throw new BadRequestException("La fecha 'Desde' es requerida si La fecha 'Hasta' está presente.");
        }
        if(from!=null&& to==null){
            throw new BadRequestException("La fecha 'Hasta' es requerida si la fecha 'Desde' está presente.");
        }
        if(containDate && to.isBefore(from)){
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
        }
        if(containDate && LocalDate.now().isBefore(from)){
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior al dia de Hoy.");
        }
        if(containDate && LocalDate.now().isBefore(to)){
            throw new BadRequestException("La fecha 'Hasta' no puede ser posterior al dia de Hoy.");
        }

        String search1 = searchBy.isEmpty()?null:searchBy;
        Boolean status1 = status.equalsIgnoreCase("ALL")?null: Boolean.valueOf(status);
        Category category1 = category.equalsIgnoreCase("ALL")?null:categoryService.findById(Integer.valueOf(category));
       return repository.findByFilter(from,to,search1,status1,category1,sort);
    }


    @Override
    public List<Plate> findAllByCategory(Integer categoryId) {
        Category category = categoryService.findById(categoryId);
        return repository.findByFilter(null,null,null,true, category,null);
    }

    @Override
    public Long count(String searchBy, LocalDate from, LocalDate to, String status, String category) {
        boolean containDate = from!=null && to!=null;
        if(from==null&& to!=null){
            throw new BadRequestException("La fecha 'Desde' es requerida si La fecha 'Hasta' está presente.");
        }
        if(from!=null&& to==null){
            throw new BadRequestException("La fecha 'Hasta' es requerida si la fecha 'Desde' está presente.");
        }
        if(containDate && to.isBefore(from)){
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
        }
        if(containDate && LocalDate.now().isBefore(from)){
            throw new BadRequestException("La fecha 'Desde' no puede ser posterior al dia de Hoy.");
        }
        if(containDate && LocalDate.now().isBefore(to)){
            throw new BadRequestException("La fecha 'Hasta' no puede ser posterior al dia de Hoy.");
        }

        String search1 = searchBy.isEmpty()?null:searchBy;
        Boolean status1 = status.equalsIgnoreCase("ALL")?null: Boolean.valueOf(status);
        Category category1 = category.equalsIgnoreCase("ALL")?null:categoryService.findById(Integer.valueOf(category));
        return repository.countByFilter(from,to,search1,status1,category1);
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Boolean validateName(String name, Integer id) {
        Boolean exist = existsByName(name);
        if (exist) {
            if (id == null) {
                return false;
            } else {
                Plate old = findById(id);
                return old.getName().equalsIgnoreCase(name);
            }
        }
        return true;
    }

    @Override
    public Plate updateStatusByID(Integer id, Boolean status) {
        Plate plate = findById(id);
        plate.setIsActive(status);
        plate.setUpdateAt(LocalDateTime.now());
        return repository.save(plate);
    }

}
