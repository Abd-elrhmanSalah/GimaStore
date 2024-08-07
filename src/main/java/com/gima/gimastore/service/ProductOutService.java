package com.gima.gimastore.service;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductOut;
import com.gima.gimastore.entity.productProcess.ProductOutProducts;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.ProductOutRequest;
import com.gima.gimastore.repository.ProductOutProductsRepository;
import com.gima.gimastore.repository.ProductOutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gima.gimastore.constant.ResponseCodes.*;
import static com.gima.gimastore.constant.ResponseCodes.REPEATED_PARTNAME;

@Service
@RequiredArgsConstructor
public class ProductOutService {
    private final ProductOutRepository productOutRepo;
    private final ProductOutProductsRepository productOutProductsRepo;

    @Transactional
    public void addProductOut(ProductOutRequest request) {

        validateRequestId(request.getRequestId());

        ProductOut productOut = new ProductOut();

        productOut.setCreatedBy(request.getCreatedBy());
        productOut.setCreationDate(request.getCreationDate());
        productOut.setNote(request.getNote());
        productOut.setRequestId(request.getRequestId());
        productOut.setDestinationName(request.getDestinationName());
        productOut.setDriverName(request.getDriverName());
        productOut.setResponsibleBy(request.getResponsibleBy());

        productOutRepo.save(productOut);

        request.getProductAmounts().forEach(productOutProduct -> {
            ProductOutProducts outProducts = new ProductOutProducts();
            outProducts.setProductOut(productOut);
            outProducts.setProduct(productOutProduct.getProduct());
            outProducts.setAmount(productOutProduct.getAmount());
            productOutProductsRepo.save(outProducts);
        });
    }

    @Transactional
    public void updateProductOut(ProductOutRequest request) {

        validateRequestIdAndID(request.getRequestId(), request.getId());

        ProductOut productOut = productOutRepo.findById(request.getId()).get();

        productOut.setCreatedBy(request.getCreatedBy());
        productOut.setCreationDate(request.getCreationDate());
        productOut.setNote(request.getNote());
        productOut.setRequestId(request.getRequestId());
        productOut.setDestinationName(request.getDestinationName());
        productOut.setDriverName(request.getDriverName());
        productOut.setResponsibleBy(request.getResponsibleBy());

        productOutRepo.save(productOut);

        productOutProductsRepo.deleteByProductOut(productOut);
        request.getProductAmounts().forEach(productOutProduct -> {
            ProductOutProducts outProducts = new ProductOutProducts();
            outProducts.setProductOut(productOut);
            outProducts.setProduct(productOutProduct.getProduct());
            outProducts.setAmount(productOutProduct.getAmount());
            productOutProductsRepo.save(outProducts);
        });
    }

    public List<ProductOutProducts> findSProductOutById(Long productOut) {
        return productOutProductsRepo.findByProductOut(productOutRepo.findById(productOut).get());

    }

    public Page<ProductOut> getProductsOut(Map<String, String> params, Pageable pageable) {
        Page<ProductOut> list = productOutRepo.findAll(
                (Specification<ProductOut>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductOut, User> productOutUserJoin = root.join("responsibleBy");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));


                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("requestId"))
                            if (!params.get("requestId").equals(""))
                                predicates.add(cb.equal(root.get("requestId"), params.get("requestId")));

                        if (params.containsKey("destinationName"))
                            if (!params.get("destinationName").equals(""))
                                predicates.add(cb.equal(root.get("destinationName"), params.get("destinationName")));

                        if (params.containsKey("driverName"))
                            if (!params.get("driverName").equals(""))
                                predicates.add(cb.equal(root.get("driverName"), params.get("driverName")));

                        if (params.containsKey("responsibleBy"))
                            if (!params.get("responsibleBy").equals(""))
                                predicates.add(cb.equal(productOutUserJoin.get("id"), params.get("responsibleBy")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }

    private void validateRequestId(String requestId) {
        if (productOutRepo.existsByRequestId(requestId))
            throw new ApplicationException(new StatusResponse(REPEATED_REQUESTEDID.getCode(), REPEATED_REQUESTEDID.getKey(),
                    REPEATED_REQUESTEDID.getMessage()));

    }

    private void validateRequestIdAndID(String requestId, Long productOutId) {
        if (!productOutRepo.findById(productOutId).get().getRequestId().equals(requestId))
            validateRequestId(requestId);
    }

}
