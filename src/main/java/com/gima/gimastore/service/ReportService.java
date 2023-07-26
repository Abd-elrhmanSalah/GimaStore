package com.gima.gimastore.service;

import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPartsReturns;
import com.gima.gimastore.model.PartReportResponse;
import com.gima.gimastore.model.PartSearchSupplyResponse;
import com.gima.gimastore.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

@Service
public class ReportService {
    private PartRepository partRepository;
    private ProductionRequestPartsRepository productionRequestPartsRepository;
    private StorePartRepository storePartRepository;
    private SupplyProcessPartsRepository supplyProcessPartsRepository;
    private StorePartSettlementRepository storePartSettlementRepo;
    private SupplyProcessPartsReturnsRepository supplyProcessPartsReturnsRepo;

    public ReportService(PartRepository partRepository, ProductionRequestPartsRepository productionRequestPartsRepository, StorePartRepository storePartRepository, SupplyProcessPartsRepository supplyProcessPartsRepository, StorePartSettlementRepository storePartSettlementRepo, SupplyProcessPartsReturnsRepository supplyProcessPartsReturnsRepo) {
        this.partRepository = partRepository;
        this.productionRequestPartsRepository = productionRequestPartsRepository;
        this.storePartRepository = storePartRepository;
        this.supplyProcessPartsRepository = supplyProcessPartsRepository;
        this.storePartSettlementRepo = storePartSettlementRepo;
        this.supplyProcessPartsReturnsRepo = supplyProcessPartsReturnsRepo;
    }

    public PartReportResponse getPartReport(Map<String, String> params, Pageable pageable) {
        PartReportResponse partReportResponse = new PartReportResponse();

        Page<SupplyProcessPart> supplyProcessParts = supplyProcessPartsRepository.findAll(
                (Specification<SupplyProcessPart>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<SupplyProcessPart, SupplyProcess> partSupplyProcessJoin = root.join("supplyProcess");
                        Join<SupplyProcessPart, Part> supplyProcessPartPartJoin = root.join("part");


                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        partSupplyProcessJoin.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        partSupplyProcessJoin.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(supplyProcessPartPartJoin.get("id"), params.get("partId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        partReportResponse.setPartName(supplyProcessParts.getContent().get(0).getPart().getPartName());

        partReportResponse.setBalanceFirstPeriod(0);
        supplyProcessParts.getContent().stream().forEach(supplyProcessPart -> {
            partReportResponse.setBalanceFirstPeriod(supplyProcessPart.getAmount() + partReportResponse.getBalanceFirstPeriod());
        });
        Page<StorePart> storeParts = storePartRepository.findAll(
                (Specification<StorePart>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();

                        Join<StorePart, Part> storePartPartJoin = root.join("part");

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(storePartPartJoin.get("id"), params.get("partId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        partReportResponse.setExistingAmount(0);
        storeParts.getContent().stream().forEach(storePart -> {
            partReportResponse.setExistingAmount(storePart.getAmount() + partReportResponse.getExistingAmount());
        });

        Page<ProductionRequestParts> productionRequestParts = productionRequestPartsRepository.findAll(
                (Specification<ProductionRequestParts>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductionRequestParts, Part> productionRequestPartsPartJoin = root.join("part");
                        Join<ProductionRequestParts, ProductionRequest> partsProductionRequestJoin = root.join("productionRequest");

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(productionRequestPartsPartJoin.get("id"), params.get("partId")));

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        partsProductionRequestJoin.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        partsProductionRequestJoin.get("creationDate"), formate.parse(params.get("ToDate"))));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        partReportResponse.setOutGoing(0);
        partReportResponse.setHarmedAmount(0);
        productionRequestParts.getContent().stream().forEach(productionRequestPart -> {
//            partReportResponse.setOutGoing(productionRequestPart.getUsedAmount() + partReportResponse.getOutGoing());
//            partReportResponse.setHarmedAmount(productionRequestPart.getHarmedAmount() + partReportResponse.getHarmedAmount());
        });

        return partReportResponse;
    }

    public Page<StorePartSettlement> getStorePartSettlementReport(Map<String, String> params, Pageable pageable) {


        Page<StorePartSettlement> storePartSettlements = storePartSettlementRepo.findAll(
                (Specification<StorePartSettlement>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<StorePartSettlement, Store> storePartSettlementStoreJoin = root.join("store");
//                        Join<StorePartSettlement, Part> storePartSettlementPartJoin = root.join("part");
//                        Join<StorePartSettlement, User> storePartSettlementUserJoin = root.join("user");


                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("storeId"))
                            if (!params.get("storeId").equals(""))
                                predicates.add(cb.equal(storePartSettlementStoreJoin.get("id"), params.get("storeId")));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return storePartSettlements;
    }

    public Page<SupplyProcessPartsReturns> getSupplyProcessPartsReturns(Map<String, String> params, Pageable pageable) {


        Page<SupplyProcessPartsReturns> supplyProcessPartsReturns = supplyProcessPartsReturnsRepo.findAll(
                (Specification<SupplyProcessPartsReturns>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<SupplyProcessPartsReturns, Part> supplyProcessPartsReturnsPartJoin = root.join("part");
                        Join<SupplyProcessPartsReturns, SupplyProcess> supplyProcessPartsReturnsSupplyProcessJoin =
                                root.join("supplyProcess");
//                        Join<StorePartSettlement, User> storePartSettlementUserJoin = root.join("user");


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
                                predicates.add(cb.equal(supplyProcessPartsReturnsPartJoin.get("id"), params.get("partId")));

                        if (params.containsKey("supplyProcessId"))
                            if (!params.get("supplyProcessId").equals(""))
                                predicates.add(cb.equal(supplyProcessPartsReturnsSupplyProcessJoin.get("billId"), params.get("supplyProcessId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return supplyProcessPartsReturns;
    }
}
