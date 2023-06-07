package com.gima.gimastore.service;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartDistDTO;
import com.gima.gimastore.repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service
public class SupplyProcessDistService {
    private SupplyProcessPartDistRepository supplyProcessPartDistRepository;
    private StorePartRepository storePartRepository;
    private SupplyProcessPartsRepository supplyProcessPartsRepository;
    private UserRepository userRepo;
    private StatusRepository statusRepo;
    private StoreRepository storeRepo;

    public SupplyProcessDistService(SupplyProcessPartDistRepository supplyProcessPartDistRepository, StorePartRepository storePartRepository, SupplyProcessPartsRepository supplyProcessPartsRepository, UserRepository userRepo, StatusRepository statusRepo, StoreRepository storeRepo) {
        this.supplyProcessPartDistRepository = supplyProcessPartDistRepository;
        this.storePartRepository = storePartRepository;
        this.supplyProcessPartsRepository = supplyProcessPartsRepository;
        this.userRepo = userRepo;
        this.statusRepo = statusRepo;
        this.storeRepo = storeRepo;
    }

    @Transactional
    public void add(SupplyProcessPartDistDTO supplyProcessPartDistDTO) {

        Optional<SupplyProcessPart> supplyProcessPartById = supplyProcessPartsRepository.findById(supplyProcessPartDistDTO.getSupplyProcessPart().getId());

        supplyProcessPartDistDTO.getStoreRequests().forEach(dto -> {
            SupplyProcessPartDist supplyProcessPartDist = new SupplyProcessPartDist();
            supplyProcessPartDist.setSupplyProcessPart(supplyProcessPartDistDTO.getSupplyProcessPart());

            supplyProcessPartDist.setStore(dto.getStore());
            supplyProcessPartDist.setAmount(dto.getAmount());
            supplyProcessPartDist.setDistBy(validateUser(supplyProcessPartDistDTO.getDistUserId()));
            supplyProcessPartDist.setStatus(validateStatus(supplyProcessPartDistDTO.getStatusId()));
            supplyProcessPartDistRepository.save(supplyProcessPartDist);

            supplyProcessPartById.get().setRemainAmount(supplyProcessPartById.get().getRemainAmount() - dto.getAmount());
            supplyProcessPartById.get().setDistAmount(supplyProcessPartById.get().getDistAmount() + dto.getAmount());
            if (supplyProcessPartById.get().getRemainAmount() == 0)
                supplyProcessPartById.get().setFullDist(true);

            supplyProcessPartsRepository.save(supplyProcessPartById.get());
        });

        if (supplyProcessPartById.get().getPartialDist() == false)
            supplyProcessPartById.get().setPartialDist(true);

//        Part part = null;
//        if (!supplyProcessPartById.isEmpty()) {
//            part = supplyProcessPartById.get().getPart();
//        }

//        Optional<StorePart> byStoreAndPart = storePartRepository.findByStoreAndPart(supplyProcessPartDist.getStore(), part);
//
//        if (!byStoreAndPart.isEmpty()) {
//            Integer amount = byStoreAndPart.get().getAmount();
//            byStoreAndPart.get().setAmount(amount + supplyProcessPartDist.getAmount());
//            storePartRepository.save(byStoreAndPart.get());
//
//        } else {
//            StorePart storePart = new StorePart();
//            storePart.setPart(part);
//            storePart.setStore(supplyProcessPartDist.getStore());
//            storePart.setAmount(supplyProcessPartDist.getAmount());
//            storePartRepository.save(storePart);
//        }

    }

    public List<SupplyProcessPartDist> getPartsDisByStoreAndStatus(Long storeId, Long statusId) {

        return supplyProcessPartDistRepository.findByStoreAndStatus(validateStore(storeId), validateStatus(statusId));
    }


    private Store validateStore(Long storeId) {
        Optional<Store> storeByID = storeRepo.findById(storeId);

        if (storeByID.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));
        return storeByID.get();
    }

    private User validateUser(Long userId) {
        Optional<User> userByID = userRepo.findById(userId);

        if (userByID.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_USER_ID.getCode(), NO_USER_ID.getKey(), NO_USER_ID.getMessage()));
        return userByID.get();
    }

    private Status validateStatus(Long statusId) {

        Optional<Status> statusById = statusRepo.findById(statusId);
        if (statusById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STATUS_ID.getCode(), NO_STATUS_ID.getKey(), NO_STATUS_ID.getMessage()));
        return statusById.get();
    }
}
