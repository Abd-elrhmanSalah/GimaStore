package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Boolean existsByDeptName(String deptName);
}
