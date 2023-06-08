package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProcessPartDistRepository extends JpaRepository<SupplyProcessPartDist, Long> {
    Page<SupplyProcessPartDist> findByStoreAndStatus(Store store, Status status, Pageable pageable);
}
