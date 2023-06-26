package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRequestPartsRepository extends JpaRepository<ProductionRequestParts, Long> {
}
