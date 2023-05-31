package com.gima.gimastore.service;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.StoreDTO;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service
public class StoreService implements CommonRepo<StoreDTO> {

    private StoreRepository storeRepo;
    private UserRepository userRepo;

    public StoreService(StoreRepository storeRepo, UserRepository userRepo) {
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void add(StoreDTO storeDTOParam) {

        validateStoreName(storeDTOParam.getStoreName());

        validateUser(storeDTOParam);

        Store store = ObjectMapperUtils.map(storeDTOParam, Store.class);

        validateExistUserWithStore(store.getUser());

        storeRepo.save(store);
    }

    @Override
    public void update(StoreDTO dto) {

        validateStoreNameAndID(dto.getStoreName(), dto.getId());

        validateUser(dto);

        if (dto.getUser().getId() != storeRepo.findById(dto.getId()).get().getUser().getId())
            validateExistUserWithStore(dto.getUser());

        Store store = ObjectMapperUtils.map(dto, Store.class);

        storeRepo.save(store);
    }


    @Override
    public void delete(Long id) {
        Optional<Store> byId = storeRepo.findById(id);
        if (Objects.isNull(byId))
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));

        storeRepo.deleteById(id);

    }

    @Override
    public StoreDTO findById(Long id) {
        Optional<Store> store = storeRepo.findById(id);
        if (store.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));

        StoreDTO storedto = ObjectMapperUtils.map(store.get(), StoreDTO.class);

        return storedto;
    }

    @Override
    public List<StoreDTO> findAll() {
        return ObjectMapperUtils.mapAll(storeRepo.findAll(), StoreDTO.class);
    }

    private void validateStoreName(String storeName) {
        if (storeRepo.existsByStoreName(storeName))
            throw new ApplicationException(new StatusResponse(REPEATED_STORENAME.getCode(), REPEATED_STORENAME.getKey(), REPEATED_STORENAME.getMessage()));

    }

    private void validateStoreNameAndID(String storeName, Long storeId) {
        if (!storeRepo.findById(storeId).get().getStoreName().equals(storeName))
            validateStoreName(storeName);
    }

    private void validateUser(StoreDTO dto) {
        Optional<User> userById = userRepo.findById(dto.getUser().getId());
        if (userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_STORE_ID.getMessage()));

        if (userById.get().getRole().getId() != 3)
            throw new ApplicationException(new StatusResponse(USER_ROLE_SUPERVISOR.getCode(), USER_ROLE_SUPERVISOR.getKey(), USER_ROLE_SUPERVISOR.getMessage()));

    }

    private void validateExistUserWithStore(User user) {
        if (storeRepo.existsByUser(user))
            throw new ApplicationException(new StatusResponse(EXIST_USER_WITH_STORE.getCode(), EXIST_USER_WITH_STORE.getKey(), EXIST_USER_WITH_STORE.getMessage()));

    }
}
