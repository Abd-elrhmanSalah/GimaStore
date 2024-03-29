package com.gima.gimastore.service;

import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartDistDTO;
import com.gima.gimastore.model.supplyProcess.SupplyProcessPartDistRequest;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.CommonBusinessValidationUtil;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SupplyProcessDistService {
    private SupplyProcessPartDistRepository supplyProcessPartDistRepo;
    private StorePartRepository storePartRepo;
    private SupplyProcessPartsRepository supplyProcessPartsRepo;
    private UserRepository userRepo;
    private CommonBusinessValidationUtil businessValidationUtil;
    private PartRepository partRepo;
    private SupplyProcessRepository supplyProcessRepository;

    public SupplyProcessDistService(SupplyProcessPartDistRepository supplyProcessPartDistRepo, StorePartRepository storePartRepo,
            SupplyProcessPartsRepository supplyProcessPartsRepo, UserRepository userRepo, CommonBusinessValidationUtil businessValidationUtil, PartRepository partRepo,
            SupplyProcessRepository supplyProcessRepository) {
        this.supplyProcessPartDistRepo = supplyProcessPartDistRepo;
        this.storePartRepo = storePartRepo;
        this.supplyProcessPartsRepo = supplyProcessPartsRepo;
        this.userRepo = userRepo;
        this.businessValidationUtil = businessValidationUtil;
        this.partRepo = partRepo;
        this.supplyProcessRepository = supplyProcessRepository;
    }

    @Transactional
    public void add(SupplyProcessPartDistRequest supplyProcessPartDistDTO) {

        Optional<SupplyProcessPart> supplyProcessPartById = supplyProcessPartsRepo.findById(supplyProcessPartDistDTO.getSupplyProcessPart().getId());

        supplyProcessPartDistDTO.getStoreRequests().forEach(dto -> {
            SupplyProcessPartDist supplyProcessPartDist = new SupplyProcessPartDist();
            supplyProcessPartDist.setSupplyProcessPart(supplyProcessPartDistDTO.getSupplyProcessPart());

            supplyProcessPartDist.setStore(dto.getStore());
            supplyProcessPartDist.setAmount(dto.getAmount());
            supplyProcessPartDist.setDistBy(businessValidationUtil.validateUser(supplyProcessPartDistDTO.getDistUserId()));
            supplyProcessPartDist.setStatus(businessValidationUtil.validateStatus(supplyProcessPartDistDTO.getStatusId()));
            supplyProcessPartDistRepo.save(supplyProcessPartDist);

            supplyProcessPartById.get().setRemainAmount(supplyProcessPartById.get().getRemainAmount() - dto.getAmount());
            supplyProcessPartById.get().setDistAmount(supplyProcessPartById.get().getDistAmount() + dto.getAmount());
            if (supplyProcessPartById.get().getRemainAmount() == 0)
                supplyProcessPartById.get().setFullDist(true);

            supplyProcessPartsRepo.save(supplyProcessPartById.get());
        });

        if (supplyProcessPartById.get().getPartialDist() == false)
            supplyProcessPartById.get().setPartialDist(true);

        //if()
    }

    public Page<SupplyProcessPartDist> getDistRequests(Map<String, String> params, Pageable pageable) {
        Page<SupplyProcessPartDist> all = supplyProcessPartDistRepo.findAll(
                (Specification<SupplyProcessPartDist>) (root, query, cb) -> {
                    try {

                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();
                        Join<SupplyProcessPartDist, SupplyProcessPart> distPartJoin = root.join("supplyProcessPart");
                        Join<SupplyProcessPart, Part> processPartJoin = distPartJoin.join("part");

                        Join<SupplyProcessPartDist, Store> distStoreJoin = root.join("store");
                        Join<SupplyProcessPartDist, Status> distStatusJoin = root.join("status");

                        if (params.containsKey("storeId"))
                            if (!params.get("storeId").equals("")) {
                                businessValidationUtil.validateStore(Long.parseLong(params.get("storeId")));
                                predicates.add(cb.equal(distStoreJoin.get("id"), params.get("storeId")));
                            }

                        if (params.containsKey("statusId"))
                            if (!params.get("statusId").equals("")) {
                                businessValidationUtil.validateStatus(Long.parseLong(params.get("statusId")));
                                predicates.add(cb.equal(distStatusJoin.get("id"), params.get("statusId")));
                            }

                        if (params.containsKey("fromDate"))
                            if (!params.get("fromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("fromDate"))));

                        if (params.containsKey("toDate"))
                            if (!params.get("toDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), formate.parse(params.get("toDate"))));

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(processPartJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        all.map(supplyProcessPartDist -> {
            SupplyProcessPartDistDTO map = ObjectMapperUtils.map(supplyProcessPartDist, SupplyProcessPartDistDTO.class);
            if (!Objects.isNull(map.getSupplyProcessPart().getPart().getPicture())) {

                Long partId = map.getSupplyProcessPart().getPart().getId();
                map.getSupplyProcessPart().getPart().
                        setPicture(ImageUtil.decompressImage(partRepo.findById(partId).get().getPicture()));
            }
            return map;
        }).toList();
        return all;
    }

    @Transactional
    public void acceptRequest(Long partDist, Long userId, String notes) {

        Optional<SupplyProcessPartDist> partDistById = supplyProcessPartDistRepo.findById(partDist);

        Part part = partDistById.get().getSupplyProcessPart().getPart();
        Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(partDistById.get().getStore(), part);

        if (!byStoreAndPart.isEmpty()) {
            Integer amount = byStoreAndPart.get().getAmount();
            byStoreAndPart.get().setAmount(amount + partDistById.get().getAmount());
            storePartRepo.save(byStoreAndPart.get());

        } else {
            StorePart storePart = new StorePart();
            storePart.setPart(part);
            storePart.setStore(partDistById.get().getStore());
            storePart.setAmount(partDistById.get().getAmount());
            storePartRepo.save(storePart);
        }
        partDistById.get().setNotes(notes);
        User user = userRepo.findById(userId).get();
        partDistById.get().setStatus(new Status(2L));
        partDistById.get().setActionBy(user);
        partDistById.get().setActionDate(new Date());

        supplyProcessPartDistRepo.save(partDistById.get());
        List<SupplyProcessPart> bySupplyProcess = supplyProcessPartsRepo.findBySupplyProcess(partDistById.get().getSupplyProcessPart().getSupplyProcess());
        Boolean allPartialDist = bySupplyProcess.stream().allMatch(supplyProcessPart ->
                supplyProcessPart.getFullDist() == true);
        if (allPartialDist) {
            SupplyProcess supplyProcess = supplyProcessRepository.findById(partDistById.get().getSupplyProcessPart().getSupplyProcess().getId()).get();
            supplyProcess.setFullDist(true);
            supplyProcessRepository.save(supplyProcess);
        }
    }

    @Transactional
    public void rejectRequest(Long partDist, Long userId, String notes) {

        Optional<SupplyProcessPartDist> partDistById = supplyProcessPartDistRepo.findById(partDist);

        partDistById.get().getSupplyProcessPart().
                setRemainAmount(partDistById.get().getSupplyProcessPart().getRemainAmount() + partDistById.get().getAmount());
        partDistById.get().getSupplyProcessPart().
                setDistAmount(partDistById.get().getSupplyProcessPart().getDistAmount() - partDistById.get().getAmount());

        if (partDistById.get().getSupplyProcessPart().getDistAmount() == 0)
            partDistById.get().getSupplyProcessPart().setPartialDist(false);
        if (partDistById.get().getSupplyProcessPart().getRemainAmount() == 0)
            partDistById.get().getSupplyProcessPart().setFullDist(true);

        partDistById.get().setNotes(notes);
        User user = userRepo.findById(userId).get();
        partDistById.get().setStatus(new Status(3L));
        partDistById.get().setActionBy(user);
        partDistById.get().setActionDate(new Date());

        supplyProcessPartDistRepo.save(partDistById.get());
    }

}
