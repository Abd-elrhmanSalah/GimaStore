package com.gima.gimastore.repository;

import com.gima.gimastore.entity.StorePartSettlement;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPartsReturns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessPartsReturnsRepository extends JpaRepository<SupplyProcessPartsReturns, Long> {
    Page<SupplyProcessPartsReturns> findAll(Specification<SupplyProcessPartsReturns> specification, Pageable pageable);

}
