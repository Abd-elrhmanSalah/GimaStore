package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Boolean existsByPartName(String partName);
}
