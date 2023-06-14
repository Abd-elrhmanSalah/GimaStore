package com.gima.gimastore.repository;

import com.gima.gimastore.entity.storePartDist.StoresPartDist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoresPartsDistRepository extends JpaRepository<StoresPartDist, Long> {
}
