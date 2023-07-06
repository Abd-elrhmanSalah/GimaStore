package com.gima.gimastore.repository;

import com.gima.gimastore.entity.storePartDist.StoresPartDist;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyProcessPartsRepository extends JpaRepository<SupplyProcessPart, Long> {
    List<SupplyProcessPart> findBySupplyProcess(SupplyProcess supplyProcess);

    void deleteAllBySupplyProcess(SupplyProcess supplyProcess);

    Page<SupplyProcessPart> findAll(Specification<SupplyProcessPart> specification, Pageable pageable);


}
