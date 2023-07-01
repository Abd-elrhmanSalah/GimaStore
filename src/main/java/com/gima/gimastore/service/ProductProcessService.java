package com.gima.gimastore.service;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductPart;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.PartSearchSupplyResponse;
import com.gima.gimastore.model.productionProcess.ProductPartResponse;
import com.gima.gimastore.model.productionProcess.ProductionAPIRequest;
import com.gima.gimastore.model.productionProcess.ProductionRequestDTO;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
        ProductionRequest savedProductionRequest = productionRequestRepo.save(productionRequest);

        productionAPIRequest.getParts().forEach(partApiRequest -> {
            ProductionRequestParts productionRequestParts = ObjectMapperUtils.map(partApiRequest
                    , ProductionRequestParts.class);

            productionRequestParts.setProductionRequest(savedProductionRequest);
            productionRequestPartsRepo.save(productionRequestParts);

            StorePart storeAndPart = storePartRepo.findByStoreAndPart(savedProductionRequest.getStore(),
                    productionRequestParts.getPart()).get();
            Integer amountToDecrease = storeAndPart.getAmount() - productionRequestParts.getAmount();
            storeAndPart.setAmount(amountToDecrease);
            storePartRepo.save(storeAndPart);
        });

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

    public Page<ProductionRequestDTO> getAllProductionRequest(Pageable pageable) {

        List<ProductionRequestDTO> list = productionRequestRepo.findAll(pageable).stream().map(productionRequest ->
                ObjectMapperUtils.map(productionRequest, ProductionRequestDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, list.size());

    }

    public List<ProductPartResponse> productPartsReturn(String requestId, Integer exactlyProduction) {
        ProductionRequest productionRequest = productionRequestRepo.findByRequestID(requestId).get();
        ProductionRequestDTO productionRequestDTO = ObjectMapperUtils.map(productionRequest, ProductionRequestDTO.class);
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

        List<ProductPartResponse> returnedProductPartResponseList = new ArrayList<>();

        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(
                ObjectMapperUtils.map(productionRequestDTO, ProductionRequest.class));
        for (int i = 0; i < allByProductionRequest.size(); i++) {
            ProductPartResponse productPartResponse = new ProductPartResponse();

            if (allByProductionRequest.get(i).getPart().getId() == exactlyProductPartResponseList.get(i).getPart().getId()) {
                Integer amountToReturn = allByProductionRequest.get(i).getAmount() -
                        exactlyProductPartResponseList.get(i).getRequestedAmount();
                productPartResponse.setPart(allByProductionRequest.get(i).getPart());
                productPartResponse.setRequestedAmount(amountToReturn);
                returnedProductPartResponseList.add(productPartResponse);
            }

        }
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
