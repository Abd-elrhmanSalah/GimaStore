package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionRequestPartsRepository extends JpaRepository<ProductionRequestParts, Long> {
    List<ProductionRequestParts> findAllByProductionRequest(ProductionRequest productionRequest);

    ProductionRequestParts findAllByProductionRequestAndPart(ProductionRequest productionRequest, Part part);

    Page<ProductionRequestParts> findAll(Specification<ProductionRequestParts> specification, Pageable pageable);

}
