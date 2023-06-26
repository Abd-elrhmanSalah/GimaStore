package com.gima.gimastore.repository;

import com.gima.gimastore.entity.productProcess.Product;
import com.gima.gimastore.entity.productProcess.ProductPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPartRepository extends JpaRepository<ProductPart, Long> {
    void deleteAllByProduct(Product product);
    List<ProductPart> findAllByProduct(Product product);
}
