package com.dev.app_restaurant.service.Impl;


import com.dev.app_restaurant.dto.ChangePasswordDTO;
import com.dev.app_restaurant.dto.SelectDTO;
import com.dev.app_restaurant.dto.UpdateMeDTO;
import com.dev.app_restaurant.dto.UserDTO;
import com.dev.app_restaurant.entity.User;
import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    public User create(UserDTO userDTO);
    public User updateById(UserDTO userDTO, Integer id,User loggin);
    public User findById(Integer id);
    public List<User> userList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status);
    public Boolean existsByUsername(String name);
    public Boolean existsByPhone(String phone);
    public Boolean validateUsername(String username, Integer id);
    public Boolean validatePhone(String phone, Integer id);
    public Long count(String searchBy, LocalDate from, LocalDate to, String status);
    public User updateStatusById(Integer id, Boolean status);
    public User findByUsername(String username);
    public User updateLoggin(UpdateMeDTO updateMeDTO, User loggin);
    public List<SelectDTO> findAllAsSelect();
    public User changePasswordLoggin(ChangePasswordDTO changePasswordDTO, User loggin);
}
