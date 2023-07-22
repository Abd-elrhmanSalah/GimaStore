package com.gima.gimastore.repository;

import com.gima.gimastore.entity.HarmedPart;
import com.gima.gimastore.entity.ReturnedPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnPartRepository extends JpaRepository<ReturnedPart, Long> {
    Page<ReturnedPart> findAll(Specification<ReturnedPart> specification, Pageable pageable);

}
