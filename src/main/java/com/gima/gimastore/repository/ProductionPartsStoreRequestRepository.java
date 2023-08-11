package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.productProcess.ProductionPartsStoreRequest;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductionPartsStoreRequestRepository extends JpaRepository<ProductionPartsStoreRequest, Long> {

    Page<ProductionPartsStoreRequest> findAllByStoreAndIsFullOut(Store store, Boolean fullOut, Pageable pageable);

    List<ProductionPartsStoreRequest> findByStoreAndProductionRequest(Store store, ProductionRequest productionRequest);
    List<ProductionPartsStoreRequest>   findByProductionRequest(ProductionRequest productionRequest);
    List<ProductionPartsStoreRequest> findByPartAndProductionRequest(Part part,ProductionRequest productionRequest);

}
