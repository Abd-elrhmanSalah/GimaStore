package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.model.PartSearchSupplyResponse;
import com.gima.gimastore.model.supplyProcess.*;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
import com.gima.gimastore.repository.SupplyProcessRepository;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_SUPPLIER_AND_BILLID;

@Service
public class SupplyProcessService {

    private SupplyProcessRepository supplyProcessRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;

    public SupplyProcessService(SupplyProcessRepository supplyProcessRepo, SupplyProcessPartsRepository supplyProcessPartsRepo) {
        this.supplyProcessRepo = supplyProcessRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
    }

    public void add(SupplyProcessRequest request, MultipartFile file) throws IOException {

        if (supplyProcessRepo.existsBySupplierAndBillId(new Supplier(request.getSupplyProcessDTO().getSupplier().getId()),
                request.getSupplyProcessDTO().getBillId())) {

            throw new ApplicationException(new StatusResponse(REPEATED_SUPPLIER_AND_BILLID.getCode(), REPEATED_SUPPLIER_AND_BILLID.getKey(),
                    REPEATED_SUPPLIER_AND_BILLID.getMessage()));
        }
        SupplyProcess savedSupplyProcess = saveAndUpdateSupplyProcess(request, file, false, null);

        savePartsList(request, savedSupplyProcess);

    }

    @Transactional
    public void update(SupplyProcessRequest request, MultipartFile file) throws IOException {
        SupplyProcess supplyProcess = validateSupplyProcessId(request.getSupplyProcessDTO().getId());
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

            SupplyProcessPartRequest partRequest = new SupplyProcessPartRequest();

            PartDTO partDto = ObjectMapperUtils.map(supplyProcessPart.getPart(), PartDTO.class);
            if (supplyProcessPart.getPart().getPicture() != null) {

                partDto.setPicture(ImageUtil.decompressImage(supplyProcessPart.getPart().getPicture()));

            }
            partRequest.setPart(partDto);
            partRequest.setId(supplyProcessPart.getId());
            partRequest.setAmount(supplyProcessPart.getAmount());
            partRequest.setCost(supplyProcessPart.getCost());
            partRequest.setRemainAmount(supplyProcessPart.getRemainAmount());
            partRequest.setDistAmount(supplyProcessPart.getDistAmount());
            partRequest.setFullDist(supplyProcessPart.getFullDist());
            partRequest.setPartialDist(supplyProcessPart.getPartialDist());

            response.getSupplyProcessParts().getParts().add(partRequest);

        });

        return response;
    }

    public Page<SupplyProcess> findAllSupplyProcess(Pageable pageable) {

        return supplyProcessRepo.findAll(pageable);


    }

    public Page<SupplyProcess> searchByPagingCriteria(Map<String, String> params, Pageable pageable) {


        Page<SupplyProcess> supplyProcessPage = supplyProcessRepo.findAll(
                (Specification<SupplyProcess>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();
                        Join<SupplyProcess, Supplier> processSupplierJoin = root.join("supplier");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("ToDate"))));


                        if (params.containsKey("supplierId"))
                            if (!params.get("supplierId").equals(""))
                                predicates.add(cb.equal(processSupplierJoin.get("id"), params.get("supplierId")));

                        if (params.containsKey("billId"))
                            if (!params.get("billId").equals(""))
                                predicates.add(cb.equal(root.get("billId"), params.get("billId")));

                        if (params.containsKey("status"))
                            if (!params.get("status").equals("")) {
                                String s = (params.get("status").toLowerCase().equals("false")) ? "0" : "1";
                                predicates.add(cb.equal(root.get("isFullDist"), Integer.valueOf(s)));
                            }

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);


        supplyProcessPage.getContent().stream().map(supplyProcess -> {
            return ObjectMapperUtils.map(supplyProcess, SupplyProcessDTO.class);
        }).collect(Collectors.toList());
        return supplyProcessPage;

    }

    public Page<PartSearchSupplyResponse> searchByPartInSupplyProcess(Map<String, String> params, Pageable pageable) {


        Page<SupplyProcessPart> supplyProcessPage = supplyProcessPartsRepo.findAll(
                (Specification<SupplyProcessPart>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<SupplyProcess, SupplyProcessPart> supplyProcessSupplyProcessPartJoin =
                                root.join("supplyProcess");

                        Join<SupplyProcess, Supplier> processSupplierJoin =
                                supplyProcessSupplyProcessPartJoin.join("supplier");

                        Join<SupplyProcessPart, Part> supplyProcessPartPartJoin = root.join("part");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        supplyProcessSupplyProcessPartJoin.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        supplyProcessSupplyProcessPartJoin.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(supplyProcessPartPartJoin.get("id"), params.get("partId")));



                        if (params.containsKey("supplierId"))
                            if (!params.get("supplierId").equals(""))
                                predicates.add(cb.equal(processSupplierJoin.get("id"), params.get("supplierId")));

                        if (params.containsKey("billId"))
                            if (!params.get("billId").equals(""))
                                predicates.add(cb.equal(
                                        supplyProcessSupplyProcessPartJoin.get("billId"), params.get("billId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);


        List<PartSearchSupplyResponse> supplyProcessList = new ArrayList<>();

        supplyProcessPage.stream().forEach(SupplyProcessPart -> {

            PartSearchSupplyResponse supplyResponse = new PartSearchSupplyResponse();

            supplyResponse.setSupplyProcessId(SupplyProcessPart.getSupplyProcess().getId());
            supplyResponse.setCreationDate(SupplyProcessPart.getSupplyProcess().getCreationDate().toString());
            supplyResponse.setCreationBy(SupplyProcessPart.getSupplyProcess().getCreatedBy().getFirstName() +
                    " " + SupplyProcessPart.getSupplyProcess().getCreatedBy().getLastName());
            supplyResponse.setBillId(SupplyProcessPart.getSupplyProcess().getBillId());
            supplyResponse.setSupplierName(SupplyProcessPart.getSupplyProcess().getSupplier().getSupplierName());
            supplyResponse.setAmount(String.valueOf(SupplyProcessPart.getAmount()));
            supplyResponse.setCost(String.valueOf(SupplyProcessPart.getCost()));
            supplyResponse.setDistAmount(String.valueOf(SupplyProcessPart.getDistAmount()));
            supplyResponse.setRemainAmount(String.valueOf(SupplyProcessPart.getRemainAmount()));

            supplyProcessList.add(supplyResponse);

        });

        PageImpl<PartSearchSupplyResponse> partSearchResponses = new PageImpl<>(supplyProcessList, pageable, supplyProcessPage.getTotalElements());
        return partSearchResponses;

    }

    private SupplyProcess saveAndUpdateSupplyProcess(SupplyProcessRequest request, MultipartFile file, boolean update, byte[] oldPic) throws IOException {
        SupplyProcessDTO supplyProcessDTO = request.getSupplyProcessDTO();
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
            supPart.setRemainAmount(partRequest.getAmount());
            supPart.setDistAmount(0);
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
