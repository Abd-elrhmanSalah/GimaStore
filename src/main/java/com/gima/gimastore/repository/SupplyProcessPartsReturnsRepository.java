package com.gima.gimastore.repository;

import com.gima.gimastore.entity.supplyProcess.SupplyProcessPartsReturns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessPartsReturnsRepository extends JpaRepository<SupplyProcessPartsReturns, Long> {
}
