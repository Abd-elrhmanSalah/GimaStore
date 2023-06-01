package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Boolean existsByUser(User user);

    Boolean existsByStoreName(String storeName);

    Optional<Store> findByUserAndIsLocked(User user,Boolean lock);

    Store findByStoreName(String storename);
}
