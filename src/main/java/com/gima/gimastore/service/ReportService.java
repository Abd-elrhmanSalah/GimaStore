package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.model.PartReportResponse;
import com.gima.gimastore.model.PartSearchSupplyResponse;
import com.gima.gimastore.repository.PartRepository;
import com.gima.gimastore.repository.ProductionRequestPartsRepository;
import com.gima.gimastore.repository.StorePartRepository;
import com.gima.gimastore.repository.SupplyProcessPartsRepository;
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

    public ReportService(PartRepository partRepository, ProductionRequestPartsRepository productionRequestPartsRepository, StorePartRepository storePartRepository, SupplyProcessPartsRepository supplyProcessPartsRepository) {
        this.partRepository = partRepository;
        this.productionRequestPartsRepository = productionRequestPartsRepository;
        this.storePartRepository = storePartRepository;
        this.supplyProcessPartsRepository = supplyProcessPartsRepository;
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
        productionRequestParts.getContent().stream().forEach(productionRequestPart -> {
            partReportResponse.setOutGoing(productionRequestPart.getUsedAmount() + partReportResponse.getOutGoing());
        });

        return partReportResponse;
    }
}
