package com.gima.gimastore.repository;

import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessPartDistRepository extends JpaRepository<SupplyProcessPartDist, Long> {
}
