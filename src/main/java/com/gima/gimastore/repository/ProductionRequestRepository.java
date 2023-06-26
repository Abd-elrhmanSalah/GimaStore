package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.ProductionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionRequestRepository extends JpaRepository<ProductionRequest,Long> {
    Optional<ProductionRequest> findByRequestID(String requestId);
}
