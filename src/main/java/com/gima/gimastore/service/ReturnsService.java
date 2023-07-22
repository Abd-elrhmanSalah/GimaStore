package com.gima.gimastore.service;

import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.model.ReturnsProcess.HarmedPartRequest;
import com.gima.gimastore.model.ReturnsProcess.ReturnPartRequest;
import com.gima.gimastore.repository.HarmedPartRepository;
import com.gima.gimastore.repository.ReturnPartRepository;
import com.gima.gimastore.repository.StorePartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReturnsService {
    private StorePartRepository storePartRepo;
    private ReturnPartRepository returnPartRepo;
    private HarmedPartRepository harmedPartRepo;

    public ReturnsService(StorePartRepository storePartRepo, ReturnPartRepository returnPartRepo, HarmedPartRepository harmedPartRepo) {
        this.storePartRepo = storePartRepo;
        this.returnPartRepo = returnPartRepo;
        this.harmedPartRepo = harmedPartRepo;
    }

    public void addPartToReturn(List<ReturnPartRequest> requestList) {
        requestList.forEach(request -> {
            request.getStoreAmounts().forEach(storeAmount -> {
                ReturnedPart returnPart = new ReturnedPart();
                returnPart.setCreationDate(request.getCreationDate());
                returnPart.setCreatedBy(request.getCreatedBy());
                returnPart.setStore(storeAmount.getStore());
                returnPart.setAmountReturned(storeAmount.getAmount());
                returnPart.setPart(request.getPart());
                returnPartRepo.save(returnPart);
                Optional<StorePart> storePart = storePartRepo.findByStoreAndPart(storeAmount.getStore(), request.getPart());
                if(storePart.isEmpty()){
                    StorePart storePart1=new StorePart();
                    storePart1.setAmount(storeAmount.getAmount());
                    storePart1.setPart(request.getPart());
                    storePart1.setStore(storeAmount.getStore());
                    storePartRepo.save(storePart1);
                }else {
                    storePart.get().setAmount(storePart.get().getAmount() + storeAmount.getAmount());
                    storePartRepo.save(storePart.get());
                }
            });
        });
    }

    public void addHarmedPart(List<HarmedPartRequest> requestList) {
        requestList.forEach(request -> {

            HarmedPart harmedPart = new HarmedPart();
            harmedPart.setCreationDate(request.getCreationDate());
            harmedPart.setCreatedBy(request.getCreatedBy());
            harmedPart.setAmountHarmed(request.getAmount());
            harmedPart.setPart(request.getPart());
            harmedPartRepo.save(harmedPart);
        });

    }

    public Page<HarmedPart> getHarmedParts(Map<String, String> params, Pageable pageable) {
        Page<HarmedPart> list = harmedPartRepo.findAll(
                (Specification<HarmedPart>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<HarmedPart, Part> harmedPartPartJoin = root.join("part");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));


                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(harmedPartPartJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }

    public Page<ReturnedPart> getReturnedPart(Map<String, String> params, Pageable pageable) {
        Page<ReturnedPart> list = returnPartRepo.findAll(
                (Specification<ReturnedPart>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ReturnedPart, Part> returnedPartPartJoin = root.join("part");
                        Join<ReturnedPart, Store> returnedPartStoreJoin = root.join("store");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));


                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(returnedPartPartJoin.get("id"), params.get("partId")));

                        if (params.containsKey("storeId"))
                            if (!params.get("storeId").equals(""))
                                predicates.add(cb.equal(returnedPartStoreJoin.get("id"), params.get("storeId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }
}
