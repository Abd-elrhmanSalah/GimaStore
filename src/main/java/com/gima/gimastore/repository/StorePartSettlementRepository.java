package com.gima.gimastore.repository;

import com.gima.gimastore.entity.StorePartSettlement;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorePartSettlementRepository extends JpaRepository<StorePartSettlement, Long> {
    Page<StorePartSettlement> findAll(Specification<StorePartSettlement> specification, Pageable pageable);
}
