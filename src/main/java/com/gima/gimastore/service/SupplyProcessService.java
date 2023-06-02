package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.model.PartRequest;
import com.gima.gimastore.model.supplyProcess.*;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gima.gimastore.constant.ResponseCodes.NO_SUPPLYPROCESS_ID;

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
            SupplyProcessPart supPart = new SupplyProcessPart();
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

    public SupplyProcessWithPartsResponse findSupplyProcessById(Long supplyProcessId) {
        Optional<SupplyProcess> supplyById = supplyProcessRepo.findById(supplyProcessId);
        if (supplyById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPPLYPROCESS_ID.getCode(), NO_SUPPLYPROCESS_ID.getKey(), NO_SUPPLYPROCESS_ID.getMessage()));

        SupplyProcessWithPartsResponse response = new SupplyProcessWithPartsResponse(new SupplyProcessPartsDTO());

        response.getSupplyProcessParts().setSupplyProcess(ObjectMapperUtils.map(supplyById.get(), SupplyProcessDTO.class));
        List<SupplyProcessPart> supplyProcessPartList = supplyProcessPartsRepo.findBySupplyProcess(ObjectMapperUtils.map(response.getSupplyProcessParts().getSupplyProcess(), SupplyProcess.class));

        supplyProcessPartList.forEach(supplyProcessPart -> {

            PartRequest partRequest = new PartRequest();
            partRequest.setPart(ObjectMapperUtils.map(supplyProcessPart.getPart(), PartDTO.class));
            partRequest.setAmount(supplyProcessPart.getAmount());
            partRequest.setCost(supplyProcessPart.getCost());
            partRequest.setFullDist(supplyProcessPart.getFullDist());
            partRequest.setPartialDist(supplyProcessPart.getPartialDist());
            response.getSupplyProcessParts().getParts().add(partRequest);

        });

        return response;
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
