package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.ProductOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOutRepository extends JpaRepository<ProductOut, Long> {
}
