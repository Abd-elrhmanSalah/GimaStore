package com.gima.gimastore.service;

import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductOut;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.model.productionProcess.ProductOutRequest;
import com.gima.gimastore.repository.ProductOutRepository;
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

    public Page<ProductOut> getProductsOut(Map<String, String> params, Pageable pageable) {
        Page<ProductOut> list = productOutRepo.findAll(
                (Specification<ProductOut>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductOut, Product> productOutProductJoin = root.join("product");

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
                                predicates.add(cb.equal(productOutProductJoin.get("id"), params.get("productId")));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }


}
