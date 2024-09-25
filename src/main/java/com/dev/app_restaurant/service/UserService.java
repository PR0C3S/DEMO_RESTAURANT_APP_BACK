package com.dev.app_restaurant.service;

import com.dev.app_restaurant.dto.*;
import com.dev.app_restaurant.entity.Role;
import com.dev.app_restaurant.entity.User;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.UserRepository;
import com.dev.app_restaurant.service.Impl.IUserService;
import com.dev.app_restaurant.util.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User create(UserDTO userDTO) {

        Role role = roleService.findById(userDTO.getRole_id());

        String username = userDTO.getUsername().trim();
        String phone = userDTO.getPhone().trim();

        //Validate username is unique
        if(!validateUsername(username,null)) {
            throw new BadRequestException("Usuario en uso.");
        }
        //Validate phone is unique
        if(!validatePhone(phone,null)) {
            throw new BadRequestException("Telefono en uso.");
        }

        //CHECK PASSWORD ARE EQUALS
        if(!checkPasswordEquals(userDTO.getPassword(), userDTO.getConfirmPassword())){
            throw new BadRequestException("Las contraseñas no coinciden.");
        }

        LocalDateTime now = LocalDateTime.now();
        User user = new User(null,username, role,now.toLocalDate(),now, userDTO.getFirstName().trim(),userDTO.getLastName().trim(), phone, passwordEncoder.encode(userDTO.getPassword()), userDTO.getIsActive());
        return repository.save(user);
    }

    @Override
    public User updateById(UserDTO userDTO, Integer id,User loggin) {
        User user = findById(id);
        Role role = roleService.findById(userDTO.getRole_id());

        if(user.getUsername().equalsIgnoreCase("admin")){
            throw new BadRequestException("No se puede editar el usuario admin con esta ruta.");
        }

        if(user.getUsername().equalsIgnoreCase(loggin.getUsername())) {
            throw new BadRequestException("No se puede editar el usuario logueado con esta ruta.");
        }
        String phone = userDTO.getPhone().trim();
        //Validate phone is unique

        if(!validatePhone(phone,id)) {
            throw new BadRequestException("Telefono en uso.");
        }

        //UPDATE PASSWORD IF IT IS SENT IN THE FORM
        if(!userDTO.getPassword().isEmpty() || !userDTO.getConfirmPassword().isEmpty()){
            if(!checkPasswordEquals(userDTO.getPassword(), userDTO.getConfirmPassword())){
                throw new BadRequestException("Las contraseñas no coinciden.");
            }else{
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
        }
        user.setPhone(phone);
        user.setRole(role);
        user.setUpdateAt(LocalDateTime.now());
        user.setFirstName(userDTO.getFirstName().trim());
        user.setLastName(userDTO.getLastName().trim());
        user.setIsActive(userDTO.getIsActive());

        return repository.save(user);
    }


    @Override
    public User findById(Integer id) {
        User user = repository.findById(id).orElse(null);
        if(user == null){
            throw new ResourceNotFoundException("El ID no existe.");
        }
        return user;
    }



    @Override
    public List<User> userList(String searchBy, String orderBy, LocalDate from, LocalDate to, String status) {
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
        return repository.findByFilter(from,to,search1,status1,sort);
    }

    @Override
    public Boolean existsByUsername(String name) {
        return repository.existsByUsername(name);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public Boolean validateUsername(String username, Integer id) {
        Boolean exist = existsByUsername(username);
        if (exist) {
            if (id == null) {
                return false;
            } else {
                User old = findById(id);
                return old.getUsername().equalsIgnoreCase(username);
            }
        }
        return true;
    }

    @Override
    public Boolean validatePhone(String phone, Integer id) {
        Boolean exist = existsByPhone(phone);
        if (exist) {
            if (id == null) {
                return false;
            } else {
                User old = findById(id);
                return old.getPhone().equalsIgnoreCase(phone);
            }
        }
        return true;
    }

    @Override
    public Long count(String searchBy, LocalDate from, LocalDate to, String status) {
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
        return repository.countByFilter(from,to,search1,status1);
    }

    @Override
    public User updateStatusById(Integer id, Boolean status) {
        User user = findById(id);
        user.setUpdateAt(LocalDateTime.now());
        user.setIsActive(status);
        return repository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User updateLoggin(UpdateMeDTO updateMeDTO, User loggin) {
        if(!validatePhone(updateMeDTO.getPhone(), loggin.getId())) {
            throw new BadRequestException("Telefono en uso.");
        }


        loggin.setUpdateAt(LocalDateTime.now());
        loggin.setFirstName(updateMeDTO.getFirstName());
        loggin.setLastName(updateMeDTO.getLastName());
        loggin.setPhone(updateMeDTO.getPhone());
        return repository.save(loggin);
    }

    @Override
    public User changePasswordLoggin(ChangePasswordDTO changePasswordDTO, User loggin) {

        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())){

            System.out.println("ERROR LAS CONTRASEÑAS NO SON IGUALES"+ changePasswordDTO.getNewPassword()+" "+changePasswordDTO.getConfirmNewPassword());
            throw new BadRequestException("Nueva contraseña y Confirmacion de la nueva contraseña no coinciden.");
        }
        System.out.println("SE CAMBIO LA CONTRASEÑA");
        loggin.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        return repository.save(loggin);
    }

    @Override
    public List<SelectDTO> findAllAsSelect() {
        return repository.findAllAsSelect();
    }

    public Boolean checkPasswordEquals(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    public void createAdminUser(){
        Role role = null;
        if(!roleService.existsByName("ADMIN")){
            role = roleService.create(new RoleDTO("ADMIN"));
            roleService.create(new RoleDTO("USER"));
            System.out.println("Roles creado con exito.");
        }else{
            role = roleService.findByName("ADMIN");
        }

        if(!existsByUsername("admin")){
            System.out.println("Usuario administrador fue creada con exito.");
            LocalDateTime now = LocalDateTime.now();
            User admin = new User(null,"admin",role,now.toLocalDate(),now,"ADMINISTRADOR","APP","", passwordEncoder.encode("admin1234"), true);
            repository.save(admin);
        }
    }
}
