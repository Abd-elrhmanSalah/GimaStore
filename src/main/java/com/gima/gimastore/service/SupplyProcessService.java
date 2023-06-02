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

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

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

    @Transactional
    public void deleteSupplyProcessById(Long supplyProcessId) {
        validateSupplyProcessId(supplyProcessId);

        supplyProcessPartsRepo.deleteAllBySupplyProcess(supplyProcessRepo.findById(supplyProcessId).get());
        supplyProcessRepo.deleteById(supplyProcessId);
    }

    public SupplyProcessWithPartsResponse findSupplyProcessById(Long supplyProcessId) throws DataFormatException, IOException {
        SupplyProcess supplyProcessById = validateSupplyProcessId(supplyProcessId);
        SupplyProcessWithPartsResponse response = new SupplyProcessWithPartsResponse(new SupplyProcessPartsDTO());

        SupplyProcessDTO map = ObjectMapperUtils.map(supplyProcessById, SupplyProcessDTO.class);
        if ( map.getPicture() != null)
            map.setPicture(ImageUtil.decompressImage(supplyProcessById.getPicture()));

        response.getSupplyProcessParts().setSupplyProcess(map);

        List<SupplyProcessPart> supplyProcessPartList = supplyProcessPartsRepo.findBySupplyProcess(ObjectMapperUtils.map(response.getSupplyProcessParts().getSupplyProcess(), SupplyProcess.class));

        supplyProcessPartList.forEach(supplyProcessPart -> {

            PartRequest partRequest = new PartRequest();

            PartDTO partDto = ObjectMapperUtils.map(supplyProcessPart.getPart(), PartDTO.class);
            if ( supplyProcessPart.getPart().getPicture() != null) {
                try {
                    partDto.setPicture(ImageUtil.decompressImage(supplyProcessPart.getPart().getPicture()));
                } catch (DataFormatException |IOException e) {
                    throw new RuntimeException(e);
                }
            }
            partRequest.setPart(partDto);

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

    private SupplyProcess validateSupplyProcessId(Long supplyProcessId) {
        Optional<SupplyProcess> supplyById = supplyProcessRepo.findById(supplyProcessId);
        if (supplyById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPPLYPROCESS_ID.getCode(), NO_SUPPLYPROCESS_ID.getKey(), NO_SUPPLYPROCESS_ID.getMessage()));

        return supplyById.get();
    }
}
