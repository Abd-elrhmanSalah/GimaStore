package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductPart;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartSearchSupplyResponse;
import com.gima.gimastore.model.ProductPartReturnedResponse;
import com.gima.gimastore.model.productionProcess.*;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gima.gimastore.constant.ResponseCodes.*;
import static com.gima.gimastore.constant.ResponseCodes.NO_STORE_ID;

@Service
public class ProductProcessService {
    private ProductionRequestRepository productionRequestRepo;
    private ProductionRequestPartsRepository productionRequestPartsRepo;
    private ProductRepository productRepo;
    private StoreRepository storeRepo;
    private ProductPartRepository productPartRepo;
    private StorePartRepository storePartRepo;

    public ProductProcessService(ProductionRequestRepository productionRequestRepo, ProductionRequestPartsRepository productionRequestPartsRepo, ProductRepository productRepo, StoreRepository storeRepo, ProductPartRepository productPartRepo, StorePartRepository storePartRepo) {
        this.productionRequestRepo = productionRequestRepo;
        this.productionRequestPartsRepo = productionRequestPartsRepo;
        this.productRepo = productRepo;
        this.storeRepo = storeRepo;
        this.productPartRepo = productPartRepo;
        this.storePartRepo = storePartRepo;
    }

    @Transactional
    public void addProductionRequest(ProductionAPIRequest productionAPIRequest) {
        Optional<ProductionRequest> byRequestID = productionRequestRepo.findByRequestID(productionAPIRequest.getProductionRequestDTO().getRequestID());
        if (!byRequestID.isEmpty())
            throw new ApplicationException(new StatusResponse(REPEATED_REQUESTEDID.getCode(), REPEATED_REQUESTEDID.getKey()
                    , REPEATED_REQUESTEDID.getMessage()));

        ProductionRequest productionRequest = ObjectMapperUtils.map(productionAPIRequest.getProductionRequestDTO(), ProductionRequest.class);
        productionRequest.setExactlyProduction(0);
        ProductionRequest savedProductionRequest = productionRequestRepo.save(productionRequest);

        productionAPIRequest.getParts().forEach(partApiRequest -> {
            ProductionRequestParts productionRequestParts = ObjectMapperUtils.map(partApiRequest
                    , ProductionRequestParts.class);

            productionRequestParts.setProductionRequest(savedProductionRequest);
            productionRequestPartsRepo.save(productionRequestParts);

            StorePart storeAndPart = storePartRepo.findByStoreAndPart(savedProductionRequest.getStore(),
                    productionRequestParts.getPart()).get();
            Integer amountToDecrease = storeAndPart.getAmount() - productionRequestParts.getRequestedAmount();
            storeAndPart.setAmount(amountToDecrease);
            storePartRepo.save(storeAndPart);
        });

    }

    @Transactional
    public void addProductionRequestReturn(ProductionReturnRequest productionReturnRequest) {
        ProductionRequest productionRequest = productionRequestRepo.findByRequestID(productionReturnRequest.getRequestID()).get();

        productionRequest.setExactlyProduction(productionReturnRequest.getExactlyProduction());
        productionRequest.setCompleted(true);
        ProductionRequest savedProductionRequest = productionRequestRepo.save(productionRequest);


        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(savedProductionRequest);
        for (int i = 0; i < allByProductionRequest.size(); i++) {

            if (allByProductionRequest.get(i).getPart().getId() == productionReturnRequest.getParts().get(i).getPart().getId()) {
                ProductionRequestParts map = ObjectMapperUtils.map(productionReturnRequest.getParts().get(i), ProductionRequestParts.class);
                map.setProductionRequest(savedProductionRequest);
                map.setHaveReturned(true);
                productionRequestPartsRepo.save(map);
                StorePart storePart = storePartRepo.findByStoreAndPart(savedProductionRequest.getStore(), map.getPart()).get();
                storePart.setAmount(storePart.getAmount() + map.getUnharmedAmount());
                storePartRepo.save(storePart);
            }

        }
    }

    public void confirmProductionRequest(String requestId) {
        ProductionRequest productionRequest = productionRequestRepo.findByRequestID(requestId).get();
        productionRequest.setCompleted(true);
        productionRequestRepo.save(productionRequest);
        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(productionRequest);
        allByProductionRequest.forEach(productionRequestParts -> {
            productionRequestParts.setUsedAmount(productionRequestParts.getRequestedAmount());
            productionRequestPartsRepo.save(productionRequestParts);
        });
    }

    public List<ProductPartReturnedResponse> productPartsReturn(String requestId, Integer exactlyProduction) {
        ProductionRequest productionRequest = productionRequestRepo.findByRequestID(requestId).get();
        ProductionRequestDTO productionRequestDTO = ObjectMapperUtils.map(productionRequest, ProductionRequestDTO.class);

        if (exactlyProduction > productionRequest.getExpectedProduction())
            throw new ApplicationException(new StatusResponse(INVALID_GREATER_EXACTLYAMOUNT.getCode(),
                    INVALID_GREATER_EXACTLYAMOUNT.getKey(), INVALID_GREATER_EXACTLYAMOUNT.getMessage()));

        if (exactlyProduction == productionRequest.getExpectedProduction())
            throw new ApplicationException(new StatusResponse(INVALID_EQUALITY_EXACTLYAMOUNT.getCode(),
                    INVALID_EQUALITY_EXACTLYAMOUNT.getKey(), INVALID_EQUALITY_EXACTLYAMOUNT.getMessage()));

        productionRequestDTO.setExactlyProduction(exactlyProduction);

        List<ProductPart> allByProduct = productPartRepo.findAllByProduct(productionRequestDTO.getProduct());

        List<ProductPartResponse> exactlyProductPartResponseList = new ArrayList<>();
        allByProduct.forEach(productPart -> {
            Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(productionRequestDTO.getStore(),
                    productPart.getPart());

            Part part = productPart.getPart();
            Integer partAmountPerProduct = productPart.getAmount();
            Integer totalAmountRequested = partAmountPerProduct * productionRequestDTO.getExactlyProduction();
            if (byStoreAndPart.get().getPart().getId() == part.getId()) {
                ProductPartResponse productPartResponse = new ProductPartResponse();

                productPartResponse.setPart(part);
                productPartResponse.setRequestedAmount(totalAmountRequested);
                exactlyProductPartResponseList.add(productPartResponse);
            }
        });

        List<ProductPartReturnedResponse> returnedProductPartResponseList = new ArrayList<>();

        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(
                ObjectMapperUtils.map(productionRequestDTO, ProductionRequest.class));
        for (int i = 0; i < allByProductionRequest.size(); i++) {
            ProductPartReturnedResponse productPartResponse = new ProductPartReturnedResponse();

            if (allByProductionRequest.get(i).getPart().getId() == exactlyProductPartResponseList.get(i).getPart().getId()) {
                Integer amountToReturn = allByProductionRequest.get(i).getRequestedAmount() -
                        exactlyProductPartResponseList.get(i).getRequestedAmount();
                ProductionRequestParts productionRequestParts = productionRequestPartsRepo.findAllByProductionRequestAndPart(productionRequest, allByProductionRequest.get(i).getPart());
                productPartResponse.setId(productionRequestParts.getId());
                productPartResponse.setPart(allByProductionRequest.get(i).getPart());
                productPartResponse.setReturnedAmount(amountToReturn);
                productPartResponse.setRequestedAmount(productionRequestParts.getRequestedAmount());


                returnedProductPartResponseList.add(productPartResponse);
            }

        }
        return returnedProductPartResponseList;
    }

    @Transactional
    public List<ProductPartResponse> getProductParts(Long productId, Integer expectedAmount, Long storeId) {
        Product product = validateExistProduct(productId).get();
        Store store = validateExistStore(storeId).get();
        List<ProductPart> allByProduct = productPartRepo.findAllByProduct(product);
        allByProduct.forEach(productPart -> {
            Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, productPart.getPart());
            if (byStoreAndPart.isEmpty())
                throw new ApplicationException(new StatusResponse(NO_STORE_WITH_PART.getCode(),
                        NO_STORE_WITH_PART.getKey(), NO_STORE_WITH_PART.getMessage()));

        });
        List<ProductPartResponse> productPartResponseList = new ArrayList<>();
        allByProduct.forEach(productPart -> {
            Optional<StorePart> byStoreAndPart = storePartRepo.findByStoreAndPart(store, productPart.getPart());
            Part part = productPart.getPart();
            Integer partAmountPerProduct = productPart.getAmount();
            Integer totalAmountRequested = partAmountPerProduct * expectedAmount;
            if (byStoreAndPart.get().getPart().equals(part)) {
                if (totalAmountRequested > byStoreAndPart.get().getAmount())
                    throw new ApplicationException(new StatusResponse(NO_AMOUNT_IN_STORE_TO_PRODUCT.getCode(),
                            NO_AMOUNT_IN_STORE_TO_PRODUCT.getKey(), NO_AMOUNT_IN_STORE_TO_PRODUCT.getMessage()));
                ProductPartResponse productPartResponse = new ProductPartResponse();
                productPartResponse.setPart(part);
                productPartResponse.setRequestedAmount(totalAmountRequested);
                productPartResponseList.add(productPartResponse);
            }
        });

        return productPartResponseList;
    }

    public Page<ProductionRequest> getAllProductionRequest(Map<String, String> params, Pageable pageable) {

        Page<ProductionRequest> list = productionRequestRepo.findAll(
                (Specification<ProductionRequest>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductionRequest, Store> productionRequestStoreJoin = root.join("store");

                        Join<ProductionRequest, Product> productionRequestProductJoin = root.join("product");

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
                                predicates.add(cb.equal(productionRequestStoreJoin.get("id"), params.get("storeId")));


                        if (params.containsKey("requestId"))
                            if (!params.get("requestId").equals(""))
                                predicates.add(cb.equal(root.get("requestID"), params.get("requestId")));

                        if (params.containsKey("productId"))
                            if (!params.get("productId").equals(""))
                                predicates.add(cb.equal(productionRequestProductJoin.get("id"), params.get("productId")));

                        if (params.containsKey("isCompleted"))
                            if (!params.get("isCompleted").equals(""))
                                if (params.get("isCompleted").equalsIgnoreCase("false"))
                                    predicates.add(cb.equal(root.get("isCompleted"), false));
                                else
                                    predicates.add(cb.equal(root.get("isCompleted"), true));


                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }

    public List<String> getAllRequestIds() {
        List<ProductionRequest> productionRequests = productionRequestRepo.findAll(Sort.by("id"));
        List<String> requestIdList = new ArrayList<>();
        productionRequests.stream().forEach(productionRequest -> {
            requestIdList.add(productionRequest.getRequestID());
        });
        return requestIdList;
    }

    public List<ProductionPartsAPIRequest> getProductPartsByRequestId(String requestId) {
        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(productionRequestRepo.findByRequestID(requestId).get());
        List<ProductionPartsAPIRequest> returnedProductPartResponseList = new ArrayList<>();
        allByProductionRequest.forEach(productionRequestParts -> {
            ProductionPartsAPIRequest productPartResponse = new ProductionPartsAPIRequest();

            productPartResponse.setPart(productionRequestParts.getPart());
            productPartResponse.setRequestedAmount(productionRequestParts.getRequestedAmount());
            productPartResponse.setHarmedAmount(productionRequestParts.getHarmedAmount());
            productPartResponse.setUnharmedAmount(productionRequestParts.getUnharmedAmount());
            productPartResponse.setHaveReturned(productionRequestParts.getHaveReturned());
            productPartResponse.setReturnedAmount(productionRequestParts.getReturnedAmount());
            productPartResponse.setUsedAmount(productionRequestParts.getUsedAmount());

            returnedProductPartResponseList.add(productPartResponse);

        });

        return returnedProductPartResponseList;
    }


    private Optional<Product> validateExistProduct(Long id) {
        Optional<Product> productById = productRepo.findById(id);
        if (productById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PRODUCT_ID.getCode(), NO_PRODUCT_ID.getKey(),
                    NO_PRODUCT_ID.getMessage()));
        return productById;
    }

    private Optional<Store> validateExistStore(Long id) {
        Optional<Store> storeById = storeRepo.findById(id);
        if (storeById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_STORE_ID.getCode(), NO_STORE_ID.getKey(), NO_STORE_ID.getMessage()));
        return storeById;
    }
}
