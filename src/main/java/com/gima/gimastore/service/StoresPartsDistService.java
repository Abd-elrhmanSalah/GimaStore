package com.gima.gimastore.service;

import com.gima.gimastore.constant.ResponseCodes;
import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.storePartDist.StoresPartDist;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.StorePartDistDTO;
import com.gima.gimastore.model.StoresPartsDistRequest;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.CommonBusinessValidationUtil;
import com.gima.gimastore.util.ImageUtil;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StoresPartsDistService {

    private final StoresPartsDistRepository storesPartsDistRepo;
    private final CommonBusinessValidationUtil businessValidationUtil;
    private final PartRepository partRepo;
    private final StorePartRepository storePartRepo;
    private final StoreRepository storeRepo;
    private final UserRepository userRepo;
    

    @Transactional
    public void add(StoresPartsDistRequest storesPartsDistDTO) {

        StoresPartDist storesPartDist = ObjectMapperUtils.map(storesPartsDistDTO, StoresPartDist.class);

        storesPartDist.setStoreFrom(businessValidationUtil.validateStore(storesPartsDistDTO.getStoreFromId()));
        storesPartDist.setStoreTo(businessValidationUtil.validateStore(storesPartsDistDTO.getStoreToId()));
        storesPartDist.setStatus(businessValidationUtil.validateStatus(storesPartsDistDTO.getStatusId()));
        storesPartDist.setDistBy(businessValidationUtil.validateUser(storesPartsDistDTO.getDistUserId()));

        storesPartsDistDTO.getParts().forEach(partRequest -> {

            Part part = partRepo.findById(ObjectMapperUtils.map(partRequest.getPart(), Part.class).getId()).get();
            Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(storesPartDist.getStoreFrom(), part);

            if (byStoreAndPart.isEmpty())
                throw new ApplicationException(new StatusResponse(ResponseCodes.NO_PART_WITH_STORE.getCode(),
                        ResponseCodes.NO_PART_WITH_STORE.getKey(),
                        ResponseCodes.NO_PART_WITH_STORE.getMessage()));

            if (byStoreAndPart.get().getAmount() < partRequest.getAmount())
                throw new ApplicationException(new StatusResponse(ResponseCodes.QUANTITY_PART_WITH_STORE.getCode(),
                        ResponseCodes.QUANTITY_PART_WITH_STORE.getKey(),
                        ResponseCodes.QUANTITY_PART_WITH_STORE.getMessage() + " " + byStoreAndPart.get().getPart().getPartName()));

            storesPartDist.setPart(part);
            storesPartDist.setAmount(partRequest.getAmount());
            storesPartsDistRepo.save(storesPartDist);
            byStoreAndPart.get().setAmount(byStoreAndPart.get().getAmount() - partRequest.getAmount());
            storePartRepo.save(byStoreAndPart.get());

        });

    }

    public Page<StoresPartDist> getDistRequests(Map<String, String> params, Pageable pageable) {
        Page<StoresPartDist> byStoreAndStatus = storesPartsDistRepo.findAll(
                (Specification<StoresPartDist>) (root, query, cb) -> {
                    try {

                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();
//                        Join<StoresPartDist, Part> distPartJoin = root.join("supplyProcessPart");
                        Join<StoresPartDist, Part> storePartJoin = root.join("part");

                        Join<StoresPartDist, Store> distStoreJoin = root.join("storeTo");
                        Join<StoresPartDist, Status> distStatusJoin = root.join("status");

                        if (params.containsKey("storeIdTo"))
                            if (!params.get("storeIdTo").equals("")) {
                                businessValidationUtil.validateStore(Long.parseLong(params.get("storeIdTo")));
                                predicates.add(cb.equal(distStoreJoin.get("id"), params.get("storeIdTo")));
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
                                predicates.add(cb.equal(storePartJoin.get("id"), params.get("partId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        byStoreAndStatus.map(storesPartDist -> {
            StorePartDistDTO map = ObjectMapperUtils.map(storesPartDist, StorePartDistDTO.class);
            if (!Objects.isNull(map.getPart().getPicture())) {

                Long partId = map.getPart().getId();
                map.getPart().setPicture(ImageUtil.decompressImage(partRepo.findById(partId).get().getPicture()));
            }
            return map;
        }).toList();
        return byStoreAndStatus;

    }

    @Transactional
    public void acceptRequest(Long partDist, Long userId, String notes) {


        StoresPartDist storePartDist = storesPartsDistRepo.findById(partDist).get();
        Part part = partRepo.findById(storePartDist.getPart().getId()).get();
        Store store = storeRepo.findById(storePartDist.getStoreTo().getId()).get();
        Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, part);

        if (!byStoreAndPart.isEmpty()) {
            Integer amount = byStoreAndPart.get().getAmount();
            byStoreAndPart.get().setAmount(amount + storePartDist.getAmount());
            storePartRepo.save(byStoreAndPart.get());

        } else {
            StorePart storePart = new StorePart();
            storePart.setPart(part);
            storePart.setStore(storePartDist.getStoreTo());
            storePart.setAmount(storePartDist.getAmount());
            storePartRepo.save(storePart);
        }
        storePartDist.setNotes(notes);
        User user = userRepo.findById(userId).get();
        storePartDist.setStatus(new Status(2L));
        storePartDist.setActionBy(user);
        storePartDist.setActionDate(new Date());

        storesPartsDistRepo.save(storePartDist);
    }

    @Transactional
    public void rejectRequest(Long partDist, Long userId, String notes) {

        StoresPartDist storePartDist = storesPartsDistRepo.findById(partDist).get();
        Part part = partRepo.findById(storePartDist.getPart().getId()).get();
        Store store = storeRepo.findById(storePartDist.getStoreFrom().getId()).get();
        Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, part);

        byStoreAndPart.get()
                .setAmount(storePartDist.getAmount() + byStoreAndPart.get().getAmount());

        storePartDist.setNotes(notes);
        User user = userRepo.findById(userId).get();
        storePartDist.setStatus(new Status(3L));
        storePartDist.setActionBy(user);
        storePartDist.setActionDate(new Date());

        storesPartsDistRepo.save(storePartDist);
    }

    public StoresPartsDistRequest findById(Long id) {
        return null;
    }

    public List<StoresPartsDistRequest> findAll() {
        return null;
    }


}
