package com.gima.gimastore.service;

import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.StorePartSettlement;
import com.gima.gimastore.model.StorePartSettlementDTO;
import com.gima.gimastore.model.StorePartSettlementRequest;
import com.gima.gimastore.repository.StorePartRepository;
import com.gima.gimastore.repository.StorePartSettlementRepository;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PartStoreService {
    private StorePartRepository storePartRepo;
    private StoreRepository storeRepo;
    private StorePartSettlementRepository storePartSettlementRepo;

    public PartStoreService(StorePartRepository storePartRepo, StoreRepository storeRepo, StorePartSettlementRepository storePartSettlementRepo) {
        this.storePartRepo = storePartRepo;
        this.storeRepo = storeRepo;
        this.storePartSettlementRepo = storePartSettlementRepo;
    }

    public Page<StorePart> findPartsByStore(Long storeId, Pageable pageable) {
        Page<StorePart> byStore = storePartRepo.findByStore(storeRepo.findById(storeId).get(), pageable);
        byStore.getContent().forEach(storePart -> {
            if (storePart.getPart().getPicture() != null)
                storePart.getPart().setPicture(ImageUtil.decompressImage(storePart.getPart().getPicture()));
        });
        return byStore;
    }

    @Transactional
    public void storeSettlement(StorePartSettlementRequest storePartSettlementRequest) {

        storePartSettlementRequest.getParts().forEach(partSettlement -> {
            StorePartSettlement storePartSettlement = new StorePartSettlement();
            storePartSettlement.setStore(storePartSettlementRequest.getStore());
            storePartSettlement.setCreationDate(storePartSettlementRequest.getCreationDate());
            storePartSettlement.setCreatedBy(storePartSettlementRequest.getCreatedBy());
            storePartSettlement.setPart(partSettlement.getPart());
            storePartSettlement.setAmountPrevious(partSettlement.getAmountPrevious());
            storePartSettlement.setAmountUpdate(partSettlement.getAmountUpdate());
            storePartSettlement.setAmountDiff(partSettlement.getAmountDiff());

            storePartSettlementRepo.save(storePartSettlement);

            StorePart byStoreAndPart = storePartRepo.findByStoreAndPart(storePartSettlement.getStore(), storePartSettlement.getPart()).get();
            byStoreAndPart.setAmount(storePartSettlement.getAmountUpdate());
            storePartRepo.save(byStoreAndPart);
        });

    }
}
