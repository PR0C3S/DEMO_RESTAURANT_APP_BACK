package com.dev.app_restaurant.repository;

import com.dev.app_restaurant.dto.RoleSelectDTO;
import com.dev.app_restaurant.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Boolean existsByName(String name);
    Role findByName(String name);

    @Query(value = "SELECT new com.dev.app_restaurant.dto.RoleSelectDTO(name,id) from Role")
    List<RoleSelectDTO> findAllAsSelect();
}
