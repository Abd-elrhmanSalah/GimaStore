package com.gima.gimastore.service;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.LOGIN_FAILED;

@Service
public class LoginService {

    private UserRepository userRepo;

    public LoginService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO login(String username, String password) {
        Optional<User> byUserNameAndPassword = userRepo.findByUserNameAndPassword(username, password);
        if (byUserNameAndPassword.isEmpty())
            throw new ApplicationException(new StatusResponse(LOGIN_FAILED.getCode(), LOGIN_FAILED.getKey(), LOGIN_FAILED.getMessage()));
        return ObjectMapperUtils.map(byUserNameAndPassword.get(), UserDTO.class);
    }
}
