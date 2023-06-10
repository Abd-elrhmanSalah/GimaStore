package com.gima.gimastore.service;

import com.gima.gimastore.entity.Role;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.RoleRepository;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service

public class UserService {

    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private StoreRepository storeRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, StoreRepository storeRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.storeRepo = storeRepo;
    }

    public void addUser(UserDTO userDTOParam, MultipartFile file) throws IOException {

        validateUserName(userDTOParam.getUserName());
        User map = ObjectMapperUtils.map(userDTOParam, User.class);
        User savedUser = userRepo.save(map);

        if (!file.isEmpty())
            savedUser.setAvatar(ImageUtil.compressImage(file.getBytes()));

        userRepo.save(savedUser);

    }

    @Transactional
    public void updateUser(UserDTO userDTO, MultipartFile file) throws IOException {

        Optional<User> userById = userRepo.findById(userDTO.getId());
        if (userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_USER_ID.getMessage()));

        validateUserNameAndID(userDTO.getUserName(), userDTO.getId());

        if (userDTO.getChangePassword() == true) {
            if (!userRepo.existsByPasswordAndId(userDTO.getOldPassword(), userDTO.getId()))
                throw new ApplicationException(new StatusResponse(PASSWORD_INCORRECT.getCode(), PASSWORD_INCORRECT.getKey(), PASSWORD_INCORRECT.getMessage()));
        }

        if (userById.get().getAvatar() != null)
            userDTO.setAvatar(userById.get().getAvatar());

        User savedUser = userRepo.save(ObjectMapperUtils.map(userDTO, User.class));

        if (!file.isEmpty())
            savedUser.setAvatar(ImageUtil.compressImage(file.getBytes()));

        userRepo.save(savedUser);
    }

    public void deleteUser(Long id) {
        Optional<User> userById = userRepo.findById(id);
        if (userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_USER_ID.getMessage()));

        if (userById.get().getRole().getId() == 3)
            if (storeRepo.existsByUser(userById.get()))
                throw new ApplicationException(new StatusResponse(USER_EXIST_IN_STORE.getCode(), USER_EXIST_IN_STORE.getKey(), USER_EXIST_IN_STORE.getMessage()));
        userById.get().setLocked(true);
        userRepo.save(userById.get());

    }

    public Page<User> getAllUsers(Pageable pageable) {

        Page<User> all = userRepo.findAll(pageable);
        all.getContent().stream().forEach(user -> {

            if (!Objects.isNull(user.getAvatar())) {
                user.setAvatar(ImageUtil.decompressImage(user.getAvatar()));
            }

        });
        return all;
    }

    public UserDTO getUserById(Long id) throws IOException, DataFormatException {
        Optional<User> userById = userRepo.findById(id);
        if (userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_USER_ID.getMessage()));

        UserDTO userDto = ObjectMapperUtils.map(userById.get(), UserDTO.class);

        if (!Objects.isNull(userById.get().getAvatar()))
            userDto.setAvatar(ImageUtil.decompressImage(userById.get().getAvatar()));

        if (userById.get().getRole().getId() == 3) {
            Optional<Store> byUserAndIsLocked = storeRepo.findByUserAndIsLocked(userById.get(), false);
            if (!byUserAndIsLocked.isEmpty())
                userDto.setStoreId(byUserAndIsLocked.get().getId());
        }
        return userDto;
    }

    public List<UserDTO> getAllUsersByRoleId(Long roleId) {
        Optional<Role> role = roleRepo.findById(roleId);
        if (role.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_ROLE_ID.getCode(), NO_ROLE_ID.getKey(), NO_ROLE_ID.getMessage()));

        List<User> users = userRepo.findByRole(ObjectMapperUtils.map(role.get(), Role.class)).stream().filter(user -> !user.getLocked()).collect(Collectors.toList());
        return ObjectMapperUtils.mapAll(users, UserDTO.class);
    }

    private void validateUserName(String username) {
        if (userRepo.existsByUserName(username))
            throw new ApplicationException(new StatusResponse(REPEATED_USERNAME.getCode(), REPEATED_USERNAME.getKey(), REPEATED_USERNAME.getMessage()));

    }

    private void validateUserNameAndID(String username, Long userId) {
        if (!userRepo.findById(userId).get().getUserName().equals(username))
            validateUserName(username);
    }


}



