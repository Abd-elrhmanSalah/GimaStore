package com.gima.gimastore.service;

import com.gima.gimastore.constant.ResponseCodes;
import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.storePartDist.StoresPartDist;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.StoresPartsDistRequest;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.CommonBusinessValidationUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StoresPartsDistService {

    private StoresPartsDistRepository storesPartsDistRepo;
    private CommonBusinessValidationUtil businessValidationUtil;
    private PartRepository partRepo;
    private StorePartRepository storePartRepo;
    private StoreRepository storeRepo;
    private UserRepository userRepo;

    public StoresPartsDistService(StoresPartsDistRepository storesPartsDistRepo, CommonBusinessValidationUtil businessValidationUtil, PartRepository partRepo, StorePartRepository storePartRepo, StoreRepository storeRepo, UserRepository userRepo) {
        this.storesPartsDistRepo = storesPartsDistRepo;
        this.businessValidationUtil = businessValidationUtil;
        this.partRepo = partRepo;
        this.storePartRepo = storePartRepo;
        this.storeRepo = storeRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void add(StoresPartsDistRequest storesPartsDistDTO) {

        StoresPartDist storesPartDist = ObjectMapperUtils.map(storesPartsDistDTO, StoresPartDist.class);

        storesPartDist.setStoreFrom(businessValidationUtil.validateStore(storesPartsDistDTO.getStoreFromId()));
        storesPartDist.setStoreTo(businessValidationUtil.validateStore(storesPartsDistDTO.getStoreToId()));
        storesPartDist.setStatus(businessValidationUtil.validateStatus(storesPartsDistDTO.getStatusId()));
        storesPartDist.setDistBy(businessValidationUtil.validateUser(storesPartsDistDTO.getDistUserId()));

        storesPartsDistDTO.getParts().forEach(partRequest -> {

            Part part = partRepo.findById(ObjectMapperUtils.map(partRequest.getPart(), Part.class).getId()).get();
            Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(storesPartDist.getStoreFrom(), part);

            if (byStoreAndPart.isEmpty())
                throw new ApplicationException(new StatusResponse(ResponseCodes.NO_PART_WITH_STORE.getCode(),
                        ResponseCodes.NO_PART_WITH_STORE.getKey(),
                        ResponseCodes.NO_PART_WITH_STORE.getMessage()));

            if (byStoreAndPart.get().getAmount() < partRequest.getAmount())
                throw new ApplicationException(new StatusResponse(ResponseCodes.QUANTITY_PART_WITH_STORE.getCode(),
                        ResponseCodes.QUANTITY_PART_WITH_STORE.getKey(),
                        ResponseCodes.QUANTITY_PART_WITH_STORE.getMessage() + " " + byStoreAndPart.get().getPart().getPartName()));

            storesPartDist.setPart(part);
            storesPartDist.setAmount(partRequest.getAmount());
            storesPartsDistRepo.save(storesPartDist);
            byStoreAndPart.get().setAmount(byStoreAndPart.get().getAmount() - partRequest.getAmount());
            storePartRepo.save(byStoreAndPart.get());

        });

    }

    @Transactional
    public void acceptRequest(Long partDist, Long userId, String notes) {


        StoresPartDist storePartDist = storesPartsDistRepo.findById(partDist).get();
        Part part = partRepo.findById(partDist).get();
        Store store = storeRepo.findById(storePartDist.getStoreTo().getId()).get();
        Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, part);

        if (!byStoreAndPart.isEmpty()) {
            Integer amount = byStoreAndPart.get().getAmount();
            byStoreAndPart.get().setAmount(amount + storePartDist.getAmount());
            storePartRepo.save(byStoreAndPart.get());

        } else {
            StorePart storePart = new StorePart();
            storePart.setPart(part);
            storePart.setStore(storePartDist.getStoreTo());
            storePart.setAmount(storePartDist.getAmount());
            storePartRepo.save(byStoreAndPart.get());
        }
        storePartDist.setNotes(notes);
        User user = userRepo.findById(userId).get();
        storePartDist.setStatus(new Status(2L));
        storePartDist.setActionBy(user);
        storePartDist.setActionDate(new Date());

        storesPartsDistRepo.save(storePartDist);
    }

    @Transactional
    public void rejectRequest(Long partDist, Long userId, String notes) {

        StoresPartDist storePartDist = storesPartsDistRepo.findById(partDist).get();
        Part part = partRepo.findById(partDist).get();
        Store store = storeRepo.findById(storePartDist.getStoreFrom().getId()).get();
        Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, part);

        byStoreAndPart.get()
                .setAmount(storePartDist.getAmount()+ byStoreAndPart.get().getAmount());

        storePartDist.setNotes(notes);
        User user = userRepo.findById(userId).get();
        storePartDist.setStatus(new Status(3L));
        storePartDist.setActionBy(user);
        storePartDist.setActionDate(new Date());

        storesPartsDistRepo.save(storePartDist);
    }
    public StoresPartsDistRequest findById(Long id) {
        return null;
    }

    public List<StoresPartsDistRequest> findAll() {
        return null;
    }


}
