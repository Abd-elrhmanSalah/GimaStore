package com.gima.gimastore.repository;

import com.gima.gimastore.entity.HarmedPart;
import com.gima.gimastore.entity.productProcess.ProductOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutRepository extends JpaRepository<ProductOut, Long> {
    Page<ProductOut> findAll(Specification<ProductOut> specification, Pageable pageable);

}
