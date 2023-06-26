package com.gima.gimastore.service;

import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.ProductionAPIRequest;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.gima.gimastore.constant.ResponseCodes.REPEATED_REQUESTEDID;

@Service
public class ProductRequestService {
    private ProductionRequestRepository productionRequestRepo;
    private ProductionRequestPartsRepository productionRequestPartsRepo;
    private ProductRepository productRepo;
    private ProductPartRepository productPartRepo;
    private StorePartRepository storePartRepo;

    public ProductRequestService(ProductionRequestRepository productionRequestRepo, ProductionRequestPartsRepository productionRequestPartsRepo, ProductRepository productRepo, ProductPartRepository productPartRepo, StorePartRepository storePartRepo) {
        this.productionRequestRepo = productionRequestRepo;
        this.productionRequestPartsRepo = productionRequestPartsRepo;
        this.productRepo = productRepo;
        this.productPartRepo = productPartRepo;
        this.storePartRepo = storePartRepo;
    }

    @Transactional
    public void addProductionRequest(ProductionAPIRequest productionAPIRequest) {
        Optional<ProductionRequest> byRequestID = productionRequestRepo.findByRequestID(productionAPIRequest.getProductionRequestDTO().getRequestID());
        if (byRequestID.isEmpty())
            throw new ApplicationException(new StatusResponse(REPEATED_REQUESTEDID.getCode(), REPEATED_REQUESTEDID.getKey()
                    , REPEATED_REQUESTEDID.getMessage()));

        ProductionRequest productionRequest = ObjectMapperUtils.map(productionAPIRequest.getProductionRequestDTO(), ProductionRequest.class);
        ProductionRequest savedProductionRequest = productionRequestRepo.save(productionRequest);

        productionAPIRequest.getParts().forEach(partApiRequest -> {
            ProductionRequestParts productionRequestParts = new ProductionRequestParts();
            productionRequestParts.setProductionRequest(savedProductionRequest);
            productionRequestParts.setPart(partApiRequest.getPart());
            productionRequestParts.setAmount(partApiRequest.getAmount());
            productionRequestParts.setHarmedAmount(partApiRequest.getHarmedAmount());
            productionRequestParts.setUnharmedAmount(partApiRequest.getUnharmedAmount());
            productionRequestParts.setHaveReturned(false);

            productionRequestPartsRepo.save(productionRequestParts);
        });

    }
    @Transactional
    public void getProductParts(Long productId,Integer expectedAmount) {
        Product product= pr

    }
}
