package com.gima.gimastore.service;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.StoreDTO;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepo;
    private final UserRepository userRepo;

    public void add(StoreDTO storeDTOParam) {

        validateStoreName(storeDTOParam.getStoreName());

        validateUser(storeDTOParam);

        Store store = ObjectMapperUtils.map(storeDTOParam, Store.class);

        validateExistUserWithStore(store.getUser());

        storeRepo.save(store);
    }


    public void update(StoreDTO dto) {

        validateStoreNameAndID(dto.getStoreName(), dto.getId());

        validateUser(dto);

        if (dto.getUser().getId() != storeRepo.findById(dto.getId()).get().getUser().getId())
            validateExistUserWithStore(dto.getUser());

        Store store = ObjectMapperUtils.map(dto, Store.class);

        storeRepo.save(store);
    }


    public void delete(Long id) {
        Optional<Store> store = validateExistStore(id);
        store.get().setIsLocked(true);
        storeRepo.save(store.get());

    }


    public StoreDTO findById(Long id) {
        Optional<Store> store = validateExistStore(id);
        StoreDTO storedto = ObjectMapperUtils.map(store.get(), StoreDTO.class);

        return storedto;
    }


    public Page<Store> findAll(Pageable pageable) {

        Page<Store> all = storeRepo.findAll(pageable);
        return all;
    }

    private void validateStoreName(String storeName) {
        if (storeRepo.existsByStoreName(storeName))
//            if (storeRepo.findByStoreName(storeName).getLocked() == false)
            throw new ApplicationException(new StatusResponse(REPEATED_STORENAME.getCode(), REPEATED_STORENAME.getKey(), REPEATED_STORENAME.getMessage()));

    }

    private void validateStoreNameAndID(String storeName, Long storeId) {
        if (!storeRepo.findById(storeId).get().getStoreName().equals(storeName))
            validateStoreName(storeName);
    }

    private Optional<User> validateUser(StoreDTO dto) {
        Optional<User> userById = userRepo.findById(dto.getUser().getId());
        if (userById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_STORE_ID.getMessage()));

        if (userById.get().getRole().getId() != 3)
            throw new ApplicationException(new StatusResponse(USER_ROLE_SUPERVISOR.getCode(), USER_ROLE_SUPERVISOR.getKey(), USER_ROLE_SUPERVISOR.getMessage()));
        return userById;
    }

    private void validateExistUserWithStore(User user) {
        if (storeRepo.existsByUser(user)) {
//            Optional<Store> s = storeRepo.findByUserAndIsLocked(user, true);
//            if (!s.isEmpty())
//                if (!s.get().getLocked())
            throw new ApplicationException(new StatusResponse(EXIST_USER_WITH_STORE.getCode(), EXIST_USER_WITH_STORE.getKey(), EXIST_USER_WITH_STORE.getMessage()));
        }
    }

    private Optional<Store> validateExistStore(Long id) {
        Optional<Store> storeById = storeRepo.findById(id);
        if (storeById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));
        return storeById;
    }
}
