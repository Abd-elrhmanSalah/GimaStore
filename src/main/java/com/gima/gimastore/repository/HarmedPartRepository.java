package com.gima.gimastore.repository;

import com.gima.gimastore.entity.HarmedPart;
import com.gima.gimastore.entity.productProcess.ProductionRequestParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarmedPartRepository extends JpaRepository<HarmedPart, Long> {
    Page<HarmedPart> findAll(Specification<HarmedPart> specification, Pageable pageable);

}
