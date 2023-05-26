package com.gima.gimastore.service;

import com.gima.gimastore.entity.Role;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
    	this. userRepo=userRepo;
    }
    public UserDTO addUser(UserDTO userDTO) {
    	
        User map = ObjectMapperUtils.map(userDTO, User.class);
        map.setRole(new Role(userDTO.getRoleId()));
        User user = userRepo.save(map);
        
        return ObjectMapperUtils.map(user, UserDTO.class);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        return ObjectMapperUtils.map(userRepo.save(ObjectMapperUtils.map(userDTO, User.class)), UserDTO.class);
    }

    public void deleteUser(UserDTO userDTO) {
        userRepo.delete(ObjectMapperUtils.map(userDTO, User.class));

    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(user -> {
            return ObjectMapperUtils.map(user, UserDTO.class);
        }).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return ObjectMapperUtils.map(userRepo.findById(id).get(), UserDTO.class);
    }
}



