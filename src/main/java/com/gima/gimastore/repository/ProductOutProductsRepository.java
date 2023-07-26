package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.ProductOut;
import com.gima.gimastore.entity.productProcess.ProductOutProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutProductsRepository extends JpaRepository<ProductOutProducts, Long> {
    void deleteByProductOut(ProductOut productOut);
    Page<ProductOutProducts> findAll(Specification<ProductOutProducts> specification, Pageable pageable);

}
