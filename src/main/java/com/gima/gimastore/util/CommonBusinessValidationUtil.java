package com.gima.gimastore.util;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.repository.StatusRepository;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.*;
@Component
public class CommonBusinessValidationUtil {
    private StoreRepository storeRepo;
    private UserRepository userRepo;
    private StatusRepository statusRepo;

    public CommonBusinessValidationUtil(StoreRepository storeRepo, UserRepository userRepo, StatusRepository statusRepo) {
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
        this.statusRepo = statusRepo;
    }

    public Store validateStore(Long storeId) {
        Optional<Store> storeByID = storeRepo.findById(storeId);

        if (storeByID.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));
        return storeByID.get();
    }

    public User validateUser(Long userId) {
        Optional<User> userByID = userRepo.findById(userId);

        if (userByID.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_USER_ID.getMessage()));
        return userByID.get();
    }

    public Status validateStatus(Long statusId) {

        Optional<Status> statusById = statusRepo.findById(statusId);
        if (statusById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STATUS_ID.getCode(), NO_STATUS_ID.getKey(), NO_STATUS_ID.getMessage()));
        return statusById.get();
    }
}
