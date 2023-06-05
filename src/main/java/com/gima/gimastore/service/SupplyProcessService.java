package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Supplier;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        SupplyProcess savedSupplyProcess = saveAndUpdateSupplyProcess(request, file, false, null);

        savePartsList(request, savedSupplyProcess);

    }

    @Transactional
    public void update(SupplyProcessRequest request, MultipartFile file) throws IOException {
        SupplyProcess supplyProcess = validateSupplyProcessId(request.getSupplyProcess().getId());
        SupplyProcess savedSupplyProcess = saveAndUpdateSupplyProcess(request, file, true, supplyProcess.getPicture());

        supplyProcessPartsRepo.deleteAllBySupplyProcess(savedSupplyProcess);
        savePartsList(request, savedSupplyProcess);

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
        if (map.getPicture() != null)
            map.setPicture(ImageUtil.decompressImage(supplyProcessById.getPicture()));

        response.getSupplyProcessParts().setSupplyProcess(map);

        List<SupplyProcessPart> supplyProcessPartList = supplyProcessPartsRepo.findBySupplyProcess(ObjectMapperUtils.map(response.getSupplyProcessParts().getSupplyProcess(), SupplyProcess.class));

        supplyProcessPartList.forEach(supplyProcessPart -> {

            PartRequest partRequest = new PartRequest();

            PartDTO partDto = ObjectMapperUtils.map(supplyProcessPart.getPart(), PartDTO.class);
            if (supplyProcessPart.getPart().getPicture() != null) {
                try {
                    partDto.setPicture(ImageUtil.decompressImage(supplyProcessPart.getPart().getPicture()));
                } catch (DataFormatException | IOException e) {
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

    public List<SupplyProcessResponse> searchByPagingCriteria(Map<String, String> params, Pageable pageable) {
        SupplyProcessResponse response = new SupplyProcessResponse();

        Page<SupplyProcess> supplyProcessPage = supplyProcessRepo.findAll(
                (Specification<SupplyProcess>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
                        List<Predicate> predicates = new ArrayList<>();
                        Join<SupplyProcess, Supplier> processSupplierJoin = root.join("supplier");

                        if (params.containsKey("supplyProcessFromDate"))
                            if (!params.get("supplyProcessFromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("supplyProcessFromDate"))));


                        if (params.containsKey("supplyProcessToDate"))
                            if (!params.get("supplyProcessToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("supplyProcessToDate"))));


                        if (params.containsKey("supplierId"))
                            if (!params.get("supplierId").equals(""))
                                predicates.add(cb.equal(processSupplierJoin.get("id"), params.get("supplierId")));

//                        if (params.containsKey("billId"))
//                            if (params.get("bill_id") != null || !params.get("requestToDate").isEmpty())
//                                predicates.add(cb.lessThanOrEqualTo(processSupplierJoin.get("bill_id"),
//                                        formate.parse(params.get("bill_id"))));

//                        if (params.containsKey("accountNumber"))
//                            if (params.get("accountNumber") != null || !params.get("accountNumber").isEmpty())
//                                if (supplierRepo.existsById(params.get("accountNumber")))
//                                    predicates.add(cb.equal(root.get("debitAccountNumber"), params.get("accountNumber")));
//                                else
//                                    predicates.add(cb.equal(root.get("creditAccountNumber"), params.get("accountNumber")));
//
//                        if (params.containsKey("status"))
//                            if (params.get("status") != null || !params.get("status").isEmpty())
//                                predicates.add(cb.equal(root.get("statusID"), params.get("status")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return supplyProcessPage.stream().map(supplyProcess -> {
            SupplyProcessDTO supplyProcessDTO = ObjectMapperUtils.map(supplyProcess, SupplyProcessDTO.class);
            response.getSupplyProcess().add(supplyProcessDTO);
            return response;
        }).collect(Collectors.toList());


    }

    private SupplyProcess saveAndUpdateSupplyProcess(SupplyProcessRequest request, MultipartFile file, boolean update, byte[] oldPic) throws IOException {
        SupplyProcessDTO supplyProcessDTO = request.getSupplyProcess();
        SupplyProcess supplyProcess = ObjectMapperUtils.map(supplyProcessDTO, SupplyProcess.class);

        if (update == true && oldPic != null)
            supplyProcess.setPicture(oldPic);

        if (!file.isEmpty())
            supplyProcess.setPicture(ImageUtil.compressImage(file.getBytes()));


        return supplyProcessRepo.save(supplyProcess);
    }

    private void savePartsList(SupplyProcessRequest request, SupplyProcess savedSupplyProcess) {
        request.getPartList().stream().forEach(partRequest -> {
            SupplyProcessPart supPart = new SupplyProcessPart();
            supPart.setSupplyProcess(savedSupplyProcess);
            supPart.setPart(ObjectMapperUtils.map(partRequest.getPart(), Part.class));
            supPart.setAmount(partRequest.getAmount());
            supPart.setCost(partRequest.getCost());
            supplyProcessPartsRepo.save(supPart);
        });

    }

    private SupplyProcess validateSupplyProcessId(Long supplyProcessId) {
        Optional<SupplyProcess> supplyById = supplyProcessRepo.findById(supplyProcessId);
        if (supplyById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_SUPPLYPROCESS_ID.getCode(), NO_SUPPLYPROCESS_ID.getKey(), NO_SUPPLYPROCESS_ID.getMessage()));

        return supplyById.get();
    }
}
