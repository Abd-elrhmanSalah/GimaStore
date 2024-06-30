package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.StorePartSettlement;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.model.StorePartSettlementDTO;
import com.gima.gimastore.model.StorePartSettlementRequest;
import com.gima.gimastore.repository.PartRepository;
import com.gima.gimastore.repository.StorePartRepository;
import com.gima.gimastore.repository.StorePartSettlementRepository;
import com.gima.gimastore.repository.StoreRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartStoreService {
    private final StorePartRepository storePartRepo;
    private final StoreRepository storeRepo;
    private final StorePartSettlementRepository storePartSettlementRepo;
    private final PartRepository partRepo;


    public Page<StorePart> findPartsByStore(Long storeId, String searchText, Pageable pageable) {
//        partRepo.findAllByPartNameContainsIgnoreCase(searchText);
        Page<StorePart> byStore = storePartRepo.findByStore(storeRepo.findById(storeId).get(), pageable);
        if (!searchText.equalsIgnoreCase("")) {
            List<StorePart> collect = byStore.getContent().stream().filter(storePart ->
                            storePart.getPart().getPartName().contains(searchText))
                    .collect(Collectors.toList());

            collect.stream().forEach(storePart -> {
                if (storePart.getPart().getPicture() != null)
                    storePart.getPart().setPicture(ImageUtil.decompressImage(storePart.getPart().getPicture()));
            });
            PageImpl<StorePart> toReturn = new PageImpl<>(collect, pageable, collect.size());
            return toReturn;
        } else {

            byStore.stream().forEach(storePart -> {
                if (storePart.getPart().getPicture() != null)
                    storePart.getPart().setPicture(ImageUtil.decompressImage(storePart.getPart().getPicture()));
            });
            return byStore;

        }
    }

    public List<StorePart> findStoresByPart(Long partId) {
        return storePartRepo.findStorePartByPart(partRepo.findById(partId).get());

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
