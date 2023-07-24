package com.gima.gimastore.service;

import com.gima.gimastore.entity.productProcess.ProductOut;
import com.gima.gimastore.model.productionProcess.ProductOutRequest;
import com.gima.gimastore.repository.ProductOutRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductOutService {
    private ProductOutRepository productOutRepo;

    public ProductOutService(ProductOutRepository productOutRepo) {
        this.productOutRepo = productOutRepo;
    }

    public void addProductOut(ProductOutRequest request) {
        request.getProductAmounts().forEach(productAmount -> {
            ProductOut productOut = new ProductOut();
            productOut.setAmount(productAmount.getAmount());
            productOut.setProduct(productAmount.getProduct());
            productOut.setCreatedBy(request.getCreatedBy());
            productOut.setCreationDate(request.getCreationDate());
            productOut.setNote(request.getNote());
            productOut.setDestinationName(request.getDestinationName());
            productOut.setDriverName(request.getDriverName());
            productOutRepo.save(productOut);
        });
    }
}
