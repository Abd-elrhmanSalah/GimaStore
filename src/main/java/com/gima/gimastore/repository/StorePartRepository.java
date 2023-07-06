package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorePartRepository extends JpaRepository<StorePart, Long> {
    Optional<StorePart> findByStoreAndPart(Store store, Part part);

    Page<StorePart> findByStore(Store store, Pageable pageable);

    List<StorePart> findStorePartByPart(Part part);

    Page<StorePart> findAll(Specification<StorePart> specification,Pageable pageable);


}
