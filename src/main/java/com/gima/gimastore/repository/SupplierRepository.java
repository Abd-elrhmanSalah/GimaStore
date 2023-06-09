package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Boolean existsBySupplierName(String supplierName);

}
