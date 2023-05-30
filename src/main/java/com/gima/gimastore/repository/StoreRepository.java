package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Boolean existsByUser(User user);

}
