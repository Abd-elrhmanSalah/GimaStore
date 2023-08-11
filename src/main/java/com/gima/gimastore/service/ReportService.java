package com.gima.gimastore.service;

import com.gima.gimastore.entity.*;
import com.gima.gimastore.entity.productProcess.*;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPartsReturns;
import com.gima.gimastore.model.PartReport;
import com.gima.gimastore.model.PartReportResponse;
import com.gima.gimastore.model.productionProcess.ProductBalanceResponse;
import com.gima.gimastore.repository.*;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {

    private ProductionRequestPartsRepository productionRequestPartsRepository;
    private ProductionRequestRepository productionRequestRepo;
    private StorePartRepository storePartRepository;
    private SupplyProcessPartsRepository supplyProcessPartsRepository;
    private StorePartSettlementRepository storePartSettlementRepo;
    private SupplyProcessPartsReturnsRepository supplyProcessPartsReturnsRepo;
    private ProductOutProductsRepository productOutProductsRepo;
    private PartRepository partRepo;
    private HarmedPartRepository harmedPartRepo;
    private ReturnPartRepository returnPartRepo;
    private ProductPartRepository productPartRepo;
    SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");

    public ReportService(ProductionRequestPartsRepository productionRequestPartsRepository, ProductionRequestRepository productionRequestRepo,
            StorePartRepository storePartRepository, SupplyProcessPartsRepository supplyProcessPartsRepository, StorePartSettlementRepository storePartSettlementRepo,
            SupplyProcessPartsReturnsRepository supplyProcessPartsReturnsRepo, ProductOutProductsRepository productOutProductsRepo, PartRepository partRepo,
            HarmedPartRepository harmedPartRepo, ReturnPartRepository returnPartRepo, ProductPartRepository productPartRepo) {
        this.productionRequestPartsRepository = productionRequestPartsRepository;
        this.productionRequestRepo = productionRequestRepo;
        this.storePartRepository = storePartRepository;
        this.supplyProcessPartsRepository = supplyProcessPartsRepository;
        this.storePartSettlementRepo = storePartSettlementRepo;
        this.supplyProcessPartsReturnsRepo = supplyProcessPartsReturnsRepo;
        this.productOutProductsRepo = productOutProductsRepo;
        this.partRepo = partRepo;
        this.harmedPartRepo = harmedPartRepo;
        this.returnPartRepo = returnPartRepo;
        this.productPartRepo = productPartRepo;
    }

    public PartReportResponse getPartReport(Map<String, String> params, Pageable pageable) {
        PartReportResponse partReportResponse = new PartReportResponse();

        Page<SupplyProcessPart> supplyProcessParts = supplyProcessPartsRepository.findAll(
                (Specification<SupplyProcessPart>) (root, query, cb) -> {
                    try {
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

    public Page<ProductOutProducts> getProductsOut(Map<String, String> params, Pageable pageable) {

        Page<ProductOutProducts> productOutProducts = productOutProductsRepo.findAll(
                (Specification<ProductOutProducts>) (root, query, cb) -> {
                    try {

                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductOutProducts, Product> productJoin = root.join("product");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("productId"))
                            if (!params.get("productId").equals(""))
                                predicates.add(cb.equal(productJoin.get("id"), params.get("productId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return productOutProducts;
    }

    public ProductBalanceResponse productBalance(Map<String, String> params, Pageable pageable) {

        Page<ProductionRequest> productionRequests = productionRequestRepo.findAll(
                (Specification<ProductionRequest>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductionRequest, Product> productJoin = root.join("product");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("productId"))
                            if (!params.get("productId").equals(""))
                                predicates.add(cb.equal(productJoin.get("id"), params.get("productId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        Page<ProductOutProducts> productsOut = productOutProductsRepo.findAll(
                (Specification<ProductOutProducts>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<ProductOutProducts, Product> productJoin = root.join("product");
                        Join<ProductOutProducts, ProductOut> j = root.join("productOut");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        j.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        j.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("productId"))
                            if (!params.get("productId").equals(""))
                                predicates.add(cb.equal(productJoin.get("id"), params.get("productId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        if (productionRequests.getContent().size() != 0) {
            ProductBalanceResponse productBalanceResponse = new ProductBalanceResponse();
            productBalanceResponse.setProduct(productionRequests.getContent().get(0).getProduct());
            ///////////
            productBalanceResponse.setAmountRequested(0);
            productionRequests.getContent().forEach(productionRequest -> {
                productBalanceResponse.setAmountRequested(productBalanceResponse.getAmountRequested()
                        + productionRequest.getExpectedProduction());
            });
            ////////////
            productBalanceResponse.setAmountOut(0);
            productsOut.getContent().forEach(productOut -> {
                productBalanceResponse.setAmountOut(productBalanceResponse.getAmountOut()
                        + productOut.getAmount());
            });
            return productBalanceResponse;
        }
        return null;

    }

    public PartReport partFullDetails(Map<String, String> params, Pageable pageable) {

        PartReport partReport = new PartReport();
        partReport.setPart(partRepo.findById(Long.parseLong(params.get("partId"))).get());

        Page<ReturnedPart> returnedParts = returnPartRepo.findAll(
                (Specification<ReturnedPart>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<ReturnedPart, Part> partJoin = root.join("part");

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
                                predicates.add(cb.equal(partJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalReturns = new AtomicReference<>(0);
        returnedParts.getContent().stream().forEach(part -> {
            totalReturns.set(totalReturns.get() + part.getAmountReturned());
        });
        partReport.setTotalReturns(totalReturns.get());
        ////////////////////////////////////////////////////////////////////////////
        Page<HarmedPart> harmedParts = harmedPartRepo.findAll(
                (Specification<HarmedPart>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<HarmedPart, Part> partJoin = root.join("part");

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
                                predicates.add(cb.equal(partJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalHarmedReturns = new AtomicReference<>(0);
        harmedParts.getContent().stream().forEach(part -> {
            totalHarmedReturns.set(totalHarmedReturns.get() + part.getAmountHarmed());
        });
        partReport.setTotalHarmed(totalHarmedReturns.get());
        ////////////////////////////////////////////////////////////////////////////
        Page<SupplyProcessPart> supplyProcessParts = supplyProcessPartsRepository.findAll(
                (Specification<SupplyProcessPart>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<SupplyProcessPart, SupplyProcess> partSupplyProcessJoin = root.join("supplyProcess");
                        Join<SupplyProcessPart, Part> partJoin = root.join("part");

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
                                predicates.add(cb.equal(partJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalSuppliesIncome = new AtomicReference<>(0);
        supplyProcessParts.getContent().stream().forEach(part -> {
            totalSuppliesIncome.set(totalSuppliesIncome.get() + part.getAmount());
        });
        partReport.setTotalSuppliesIncome(totalHarmedReturns.get());
        ////////////////////////////////////////////////////////////////////////////
        Page<SupplyProcessPartsReturns> supplyProcessPartsReturns = supplyProcessPartsReturnsRepo.findAll(
                (Specification<SupplyProcessPartsReturns>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<SupplyProcessPartsReturns, Part> partJoin = root.join("part");

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
                                predicates.add(cb.equal(partJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalSuppliesReturns = new AtomicReference<>(0);
        supplyProcessPartsReturns.getContent().stream().forEach(part -> {
            totalSuppliesReturns.set(totalSuppliesReturns.get() + part.getAmountReturn());
        });
        partReport.setTotalSuppliesReturns(totalSuppliesReturns.get());
        ////////////////////////////////////////////////////////////////////////////
        Page<ProductOutProducts> productOutProducts = productOutProductsRepo.findAll(
                (Specification<ProductOutProducts>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<ProductOutProducts, ProductOut> productOutJoin = root.join("productOut");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        productOutJoin.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        productOutJoin.get("creationDate"), formate.parse(params.get("ToDate"))));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalOut = new AtomicReference<>(0);
        productOutProducts.getContent().stream().forEach(productOutProduct -> {
            if (productPartRepo.existsByPartAndProduct(partReport.getPart(), productOutProduct.getProduct()))
                totalOut.set(totalOut.get() + productOutProduct.getAmount() *
                        productPartRepo.findByPartAndProduct(partReport.getPart(), productOutProduct.getProduct()).
                                getAmount());
        });

        partReport.setTotalOut(totalOut.get());
        ////////////////////////////////////////////////////////////////////////////
        Page<ProductionRequestParts> productionRequestParts = productionRequestPartsRepository.findAll(
                (Specification<ProductionRequestParts>) (root, query, cb) -> {
                    try {
                        List<Predicate> predicates = new ArrayList<>();
                        Join<ProductionRequestParts, ProductionRequest> productionRequestJoin = root.join("productionRequest");
                        Join<ProductionRequestParts, Part> partJoin = root.join("part");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        productionRequestJoin.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        productionRequestJoin.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("partId"))
                            if (!params.get("partId").equals(""))
                                predicates.add(cb.equal(partJoin.get("id"), params.get("partId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);
        AtomicReference<Integer> totalProductionRequested = new AtomicReference<>(0);

        productionRequestParts.getContent().stream().forEach(productionRequestPart -> {
            totalProductionRequested.set(totalProductionRequested.get() + productionRequestPart.getRequestedAmount());
        });
        partReport.setTotalProductionReuested(totalProductionRequested.get());
        return partReport;
    }
}
