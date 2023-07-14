package com.gima.gimastore.service;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.UserPrivileges;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.model.UserPrivilegesDTO;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserPrivilegesRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static com.gima.gimastore.constant.ResponseCodes.LOGIN_FAILED;
import static com.gima.gimastore.constant.ResponseCodes.LOGIN_USER_LOCKED;

@Service
public class LoginService {

    private UserRepository userRepo;
    private StoreRepository storeRepo;
    private UserPrivilegesRepository userPrivilegesRepo;

    public LoginService(UserRepository userRepo, StoreRepository storeRepo, UserPrivilegesRepository userPrivilegesRepo) {
        this.userRepo = userRepo;
        this.storeRepo = storeRepo;
        this.userPrivilegesRepo = userPrivilegesRepo;
    }

    public UserDTO login(String username, String password) throws DataFormatException, IOException {
        Optional<User> byUserNameAndPassword = userRepo.findByUserNameAndPassword(username, password);
        if (byUserNameAndPassword.isEmpty())
            throw new ApplicationException(new StatusResponse(LOGIN_FAILED.getCode(), LOGIN_FAILED.getKey(), LOGIN_FAILED.getMessage()));

        if (byUserNameAndPassword.get().getLocked() == true)
            throw new ApplicationException(new StatusResponse(LOGIN_USER_LOCKED.getCode(), LOGIN_USER_LOCKED.getKey(), LOGIN_USER_LOCKED.getMessage()));

        UserDTO userDto = ObjectMapperUtils.map(byUserNameAndPassword.get(), UserDTO.class);
        if (!Objects.isNull(byUserNameAndPassword.get().getAvatar()))
            userDto.setAvatar(ImageUtil.decompressImage(byUserNameAndPassword.get().getAvatar()));

        if (byUserNameAndPassword.get().getRole().getId() == 3) {
            Optional<Store> byUserAndIsLocked = storeRepo.findByUserAndIsLocked(byUserNameAndPassword.get(), false);
            if (!byUserAndIsLocked.isEmpty())
                userDto.setStoreId(byUserAndIsLocked.get().getId());
        }
        UserPrivileges byUser = userPrivilegesRepo.findByUser(byUserNameAndPassword.get()).get();
        userDto.setUserPrivileges(ObjectMapperUtils.map(byUser, UserPrivilegesDTO.class));
        return userDto;
    }
}
