package com.gima.gimastore.repository;

import com.gima.gimastore.entity.SupplyProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessRepository extends JpaRepository<SupplyProcess, Long> {
}
