package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartDTO;
import com.gima.gimastore.model.PartRequest;
import com.gima.gimastore.model.supplyProcess.SupplyProcessDTO;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartsDTO;
import com.gima.gimastore.model.supplyProcess.SupplyProcessRequest;
import com.gima.gimastore.model.supplyProcess.SupplyProcessWithPartsResponse;
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

    public List<SupplyProcessDTO> findAllSupplyProcess() {

        return supplyProcessRepo.findAll().stream().map(supplyProcess -> {
            return ObjectMapperUtils.map(supplyProcess, SupplyProcessDTO.class);
        }).collect(Collectors.toList());


    }

    public Page<SupplyProcess> searchByPagingCriteria(Map<String, String> params, Pageable pageable) {


        Page<SupplyProcess> supplyProcessPage = supplyProcessRepo.findAll(
                (Specification<SupplyProcess>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
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

                        if (params.containsKey("billId"))
                            if (!params.get("billId").equals(""))
                                predicates.add(cb.equal(root.get("billId"), params.get("billId")));

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
