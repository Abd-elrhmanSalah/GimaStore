package com.gima.gimastore.service;

import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.repository.StorePartRepository;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.util.ImageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartStoreService {
    private StorePartRepository storePartRepo;
    private StoreRepository storeRepo;

    public PartStoreService(StorePartRepository storePartRepo, StoreRepository storeRepo) {
        this.storePartRepo = storePartRepo;
        this.storeRepo = storeRepo;
    }

    public Page<StorePart> findPartsByStore(Long storeId, Pageable pageable){
        Page<StorePart> byStore = storePartRepo.findByStore(storeRepo.findById(storeId).get(), pageable);
        byStore.getContent().forEach(storePart -> {
            storePart.getPart().setPicture(   ImageUtil.decompressImage(storePart.getPart().getPicture()));
        });
        return byStore;
    }
}
