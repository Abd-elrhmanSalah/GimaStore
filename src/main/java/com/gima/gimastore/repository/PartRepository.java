package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Boolean existsByPartName(String partName);

    Page<Part> findAll(Pageable pageable);

    Page<Part> findAll(Specification<Part> specification, Pageable pageable);

}
