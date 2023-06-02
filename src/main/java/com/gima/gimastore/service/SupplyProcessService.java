package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessParts;
import com.gima.gimastore.model.supplyProcess.SupplyProcessDTO;
import com.gima.gimastore.model.supplyProcess.SupplyProcessRequest;
import com.gima.gimastore.model.supplyProcess.SupplyProcessResponse;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class SupplyProcessService {

    private SupplyProcessRepository supplyProcessRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;

    public SupplyProcessService(SupplyProcessRepository supplyProcessRepo, SupplyProcessPartsRepository supplyProcessPartsRepo) {
        this.supplyProcessRepo = supplyProcessRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
    }


    public void add(SupplyProcessRequest request, MultipartFile file) throws IOException {
        SupplyProcessDTO supplyProcessDTO = request.getSupplyProcess();
        SupplyProcess supplyProcess = ObjectMapperUtils.map(supplyProcessDTO, SupplyProcess.class);
//        supplyProcess.setCreationDate(LocalDateTime.now());

        if (!file.isEmpty())
            supplyProcess.setPicture(ImageUtil.compressImage(file.getBytes()));

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

    public void update(SupplyProcessRequest request) {

    }

    public void delete(Long id) {

    }

    public SupplyProcessRequest findSupplyProcessById(Long supplyProcessId) {
        return null;
    }

    public SupplyProcessResponse findAllSupplyProcess() {
        SupplyProcessResponse response = new SupplyProcessResponse();

        supplyProcessRepo.findAll().stream().map(supplyProcess -> {
            SupplyProcessDTO supplyProcessDTO = ObjectMapperUtils.map(supplyProcess, SupplyProcessDTO.class);
            response.getSupplyProcess().add(supplyProcessDTO);
            return response;
        }).collect(Collectors.toList());

        return response;
    }
}
