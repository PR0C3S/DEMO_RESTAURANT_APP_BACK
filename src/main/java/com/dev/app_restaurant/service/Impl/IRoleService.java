package com.dev.app_restaurant.service.Impl;

import com.dev.app_restaurant.dto.RoleDTO;
import com.dev.app_restaurant.dto.RoleSelectDTO;
import com.dev.app_restaurant.entity.Role;
import java.util.List;

public interface IRoleService {
    public Role create(RoleDTO roleDTO);
    public Role findById(Integer id);
    public Role findByName(String name);
    public List<Role> roleList();
    public Boolean existsByName(String name);
    public List<RoleSelectDTO> findAllAsSelect();
    public Long count();
}
