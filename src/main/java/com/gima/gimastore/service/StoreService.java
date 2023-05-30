package com.gima.gimastore.service;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.StoreDTO;
import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void add(StoreDTO dto) {
        Store store = ObjectMapperUtils.map(dto, Store.class);

        validateStoreName(store.getStoreName());
        validateUser(dto);

        if (storeRepo.existsByUser(store.getUser()))
            throw new ApplicationException(new StatusResponse(EXIST_USER_WITH_STORE.getCode(), EXIST_USER_WITH_STORE.getKey(), EXIST_USER_WITH_STORE.getMessage()));

        store.setUser(new User(dto.getUserDTO().getId()));
        storeRepo.save(store);
    }

    @Override
    public void update(StoreDTO dto) {

        validateStoreNameAndID(dto.getStoreName(), dto.getId());
        validateUser(dto);

        if (dto.getUserDTO().getId() != findById(dto.getId()).getUserDTO().getId())
            if (storeRepo.existsByUser(ObjectMapperUtils.map(dto.getUserDTO(), User.class)))
                throw new ApplicationException(new StatusResponse(EXIST_USER_WITH_STORE.getCode(), EXIST_USER_WITH_STORE.getKey(), EXIST_USER_WITH_STORE.getMessage()));

        Store store = ObjectMapperUtils.map(dto, Store.class);
        store.setUser(new User(dto.getUserDTO().getId()));
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
        if (store.isEmpty() || Objects.isNull(store))
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));
        StoreDTO storedto = ObjectMapperUtils.map(store, StoreDTO.class);
        storedto.setUserDTO(ObjectMapperUtils.map(store.get().getUser(), UserDTO.class));
        return storedto;
    }

    @Override
    public List<StoreDTO> findAll() {
        List<Store> storeList = storeRepo.findAll();
        return storeList.stream().map(store -> {
            StoreDTO storeDto = ObjectMapperUtils.map(store, StoreDTO.class);
            if (!Objects.isNull(store.getUser()))
                storeDto.setUserDTO(ObjectMapperUtils.map(store.getUser(), UserDTO.class));
            return storeDto;
        }).collect(Collectors.toList());

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
        Optional<User> byId = userRepo.findById(dto.getUserDTO().getId());
        if (byId.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_STORE_ID.getMessage()));

    }
}
