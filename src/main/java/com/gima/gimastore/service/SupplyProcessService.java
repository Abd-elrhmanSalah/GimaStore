package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.SupplyProcess;
import com.gima.gimastore.entity.SupplyProcessParts;
import com.gima.gimastore.model.SupplyProcessDTO;
import com.gima.gimastore.model.SupplyProcessRequest;
import com.gima.gimastore.repository.CommonRepo;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplyProcessService implements CommonRepo<SupplyProcessRequest> {

    private SupplyProcessRepository supplyProcessRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;

    public SupplyProcessService(SupplyProcessRepository supplyProcessRepo, SupplyProcessPartsRepository supplyProcessPartsRepo) {
        this.supplyProcessRepo = supplyProcessRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
    }


    @Override
    public void add(SupplyProcessRequest request) throws IOException {
        SupplyProcessDTO supplyProcessDTO = request.getSupplyProcess();
        SupplyProcess supplyProcess = ObjectMapperUtils.map(supplyProcessDTO, SupplyProcess.class);
        supplyProcess.setCreationDate(LocalDateTime.now());

        if (!supplyProcessDTO.getFile().isEmpty())
            supplyProcess.setPicture(ImageUtil.compressImage(supplyProcessDTO.getFile().getBytes()));

        SupplyProcess savedSupplyProcess = supplyProcessRepo.save(supplyProcess);
////////////////////////////////////////////////////////////////////////////////
        request.getPartList().stream().forEach(partRequest -> {
            SupplyProcessParts supPart = new SupplyProcessParts();
            supPart.setSupplyProcess(savedSupplyProcess);
            supPart.setPart(ObjectMapperUtils.map(partRequest.getPart(), Part.class));
            supPart.setAmount(partRequest.getAmount());
            supPart.setCost(partRequest.getCost());
            supplyProcessPartsRepo.save(supPart);
        });

    }

    @Override
    public void update(SupplyProcessRequest request) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public SupplyProcessRequest findById(Long id) {
        return null;
    }

    @Override
    public List<SupplyProcessRequest> findAll() {
        return null;
    }
}
