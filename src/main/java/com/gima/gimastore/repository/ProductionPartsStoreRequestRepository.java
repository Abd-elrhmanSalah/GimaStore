package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.productProcess.ProductionPartsStoreRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductionPartsStoreRequestRepository extends JpaRepository<ProductionPartsStoreRequest, Long> {

    List<ProductionPartsStoreRequest> findAllByStoreAndIsFullOut(Store store, Boolean fullOut);

    @Query("select  p.id,avg (p.outedAmount),p.productionRequest,p.isFullOut,p.lastUpdateDate,p.lastUpdatedBy,p.outedAmount,p.part,p.requestedAmount,p.store from ProductionPartsStoreRequest p where p.store = :store and p.isFullOut=:fullOut group by p.productionRequest")
    List<ProductionPartsStoreRequest> findByStoreAndISFullOutNot(@Param("store") Store store, @Param("fullOut") Boolean isFullOut);
}
