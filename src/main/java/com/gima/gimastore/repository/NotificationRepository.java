package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Notification;
import com.gima.gimastore.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Notification as e WHERE e.RECEIVER = (:receiver) ")
    Page<Notification> findAllByReceiver(Long receiver, Pageable p);

    @Query(nativeQuery = true, value = "SELECT * FROM Notification as e WHERE e.PRIVILEGE IN (:privileges) and e.CREATED_BY !=(:user)")
    Page<Notification> findAllByPrivilegeAndCreatedByNot(@Param("privileges") List<String> privileges,
                                                         @Param("user") User user,Pageable p);
}
