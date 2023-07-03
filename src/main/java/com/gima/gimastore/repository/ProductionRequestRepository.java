package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.ProductionRequest;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionRequestRepository extends JpaRepository<ProductionRequest, Long> {
    Optional<ProductionRequest> findByRequestID(String requestId);

    Page<ProductionRequest> findAll(Specification<ProductionRequest> specification, Pageable pageable);
}
