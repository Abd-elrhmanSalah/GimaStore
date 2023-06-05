package com.gima.gimastore.repository;

import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessRepository extends JpaRepository<SupplyProcess, Long> {
    Page<SupplyProcess> findAll(Specification<SupplyProcess> voucherDueDate, Pageable pageable);
}
