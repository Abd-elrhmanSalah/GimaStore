package com.gima.gimastore.repository;

import com.gima.gimastore.entity.supplyProcess.SupplyProcessParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessPartsRepository extends JpaRepository<SupplyProcessParts,Long> {
}
