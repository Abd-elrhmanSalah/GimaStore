package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    Boolean existsBySupervisorName(String supervisorName);
}
