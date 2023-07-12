package com.gima.gimastore.repository;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.UserPrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivilegesRepository extends JpaRepository<UserPrivileges, Long> {
    UserPrivileges findByUser(User use);
}
