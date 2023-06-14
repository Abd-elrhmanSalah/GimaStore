package com.gima.gimastore.repository;

import com.gima.gimastore.entity.storePartDist.StoresPartDist;
import com.gima.gimastore.entity.supplyProcessPartDist.SupplyProcessPartDist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresPartsDistRepository extends JpaRepository<StoresPartDist, Long> {
    Page<StoresPartDist> findAll(Specification<StoresPartDist> specification, Pageable pageable);

}
