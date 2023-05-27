package com.gima.gimastore.repository;


import com.gima.gimastore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameAndPassword(String username, String password);

    Boolean existsByUserName(String username);
}
