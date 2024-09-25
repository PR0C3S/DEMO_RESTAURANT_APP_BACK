package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.RoleDTO;
import com.dev.app_restaurant.dto.RoleSelectDTO;
import com.dev.app_restaurant.entity.Role;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.RoleRepository;
import com.dev.app_restaurant.service.Impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Role create(RoleDTO roleDTO) {
        String name = roleDTO.getName().toUpperCase().trim();
        if(existsByName(name)){
            throw new BadRequestException("Nombre en uso.");
        }
        return repository.save(new Role(null,name));
    }

    @Override
    public Role findById(Integer id) {
        Role role = repository.findById(id).orElse(null);
        if(role == null){
            throw new ResourceNotFoundException("EL ID no existe.");
        }
        return role;
    }

    @Override
    public Role findByName(String name) {
        Role role = repository.findByName(name);
        if(role == null){
            throw new ResourceNotFoundException("EL nombre no existe.");
        }
        return role;
    }

    @Override
    public List<Role> roleList() {
        return repository.findAll();
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public List<RoleSelectDTO> findAllAsSelect() {
        return repository.findAllAsSelect();
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
