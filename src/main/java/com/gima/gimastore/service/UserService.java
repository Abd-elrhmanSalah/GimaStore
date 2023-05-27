package com.gima.gimastore.service;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gima.gimastore.constant.ResponseCodes.NO_USER;

@Service

public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO addUser(UserDTO userDTO) {

        User map = ObjectMapperUtils.map(userDTO, User.class);
        map.setRole(userDTO.getRole());

        return ObjectMapperUtils.map(userRepo.save(map), UserDTO.class);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> userById = userRepo.findById(userDTO.getId());
        if (Objects.isNull(userById) || userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER.getCode(), NO_USER.getKey(), NO_USER.getMessage()));

        return ObjectMapperUtils.map(userRepo.save(ObjectMapperUtils.map(userDTO, User.class)), UserDTO.class);
    }

    public void deleteUser(Long id) {
        Optional<User> userById = userRepo.findById(id);
        if (Objects.isNull(userById) || userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER.getCode(), NO_USER.getKey(), NO_USER.getMessage()));

        userRepo.delete(ObjectMapperUtils.map(userById, User.class));

    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(user -> {
            return ObjectMapperUtils.map(user, UserDTO.class);
        }).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userById = userRepo.findById(id);
        if (Objects.isNull(userById) || userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER.getCode(), NO_USER.getKey(), NO_USER.getMessage()));

        return ObjectMapperUtils.map(userById.get(), UserDTO.class);
    }
}



