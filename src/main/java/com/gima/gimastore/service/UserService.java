package com.gima.gimastore.service;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service

public class UserService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO addUser(UserDTO userDTO, MultipartFile file) throws IOException {

        validateUserName(userDTO.getUserName());
        User map = ObjectMapperUtils.map(userDTO, User.class);

        map.setRole(userDTO.getRole());
        User savedUser = userRepo.save(map);
        if (!file.isEmpty())
            savedUser.setAvatar(ImageUtil.compressImage(file.getBytes()));

        return ObjectMapperUtils.map(userRepo.save(savedUser), UserDTO.class);
    }

    public UserDTO updateUser(UserDTO userDTO, MultipartFile file) throws IOException {

        Optional<User> userById = userRepo.findById(userDTO.getId());
        if (Objects.isNull(userById) || userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER.getCode(), NO_USER.getKey(), NO_USER.getMessage()));

        validateUserNameAndID(userDTO.getUserName(), userDTO.getId());

        if (userDTO.getChangePassword() == true) {
            if (!userRepo.existsByPasswordAndId(userDTO.getOldPassword(), userDTO.getId()))
                throw new ApplicationException(new StatusResponse(PASSWORD_INCORRECT.getCode(), PASSWORD_INCORRECT.getKey(), PASSWORD_INCORRECT.getMessage()));
        }
        User savedUser = userRepo.save(ObjectMapperUtils.map(userDTO, User.class));

        if (!file.isEmpty())
            savedUser.setAvatar(ImageUtil.compressImage(file.getBytes()));

        return ObjectMapperUtils.map(userRepo.save(savedUser), UserDTO.class);
    }

    public void deleteUser(Long id) {
        Optional<User> userById = userRepo.findById(id);
        if (Objects.isNull(userById) || userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER.getCode(), NO_USER.getKey(), NO_USER.getMessage()));

        userRepo.deleteById(userById.get().getId());

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
        UserDTO userDto = ObjectMapperUtils.map(userById.get(), UserDTO.class);
        userDto.setAvatar(ImageUtil.decompressImage(userById.get().getAvatar()));
        return userDto;
    }

    private void validateUserName(String username) {
        if (userRepo.existsByUserName(username))
            throw new ApplicationException(new StatusResponse(REPEATED_USERNAME.getCode(), REPEATED_USERNAME.getKey(), REPEATED_USERNAME.getMessage()));

    }

    private void validateUserNameAndID(String username, Long userId) {
        if (!userRepo.findById(userId).get().getUserName().equals(username))
            if (userRepo.existsByUserName(username))
                throw new ApplicationException(new StatusResponse(REPEATED_USERNAME.getCode(), REPEATED_USERNAME.getKey(), REPEATED_USERNAME.getMessage()));

    }
}



