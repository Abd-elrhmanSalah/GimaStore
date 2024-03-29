package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessRepository extends JpaRepository<SupplyProcess, Long> {
    Page<SupplyProcess> findAll(Specification<SupplyProcess> specification, Pageable pageable);

    SupplyProcess findByBillId(String billId);

    Boolean existsBySupplierAndBillId(Supplier supplier, String billId);
}
