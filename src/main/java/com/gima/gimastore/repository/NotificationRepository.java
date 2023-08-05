package com.gima.gimastore.repository;

import com.gima.gimastore.entity.Notification;
import com.gima.gimastore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //    List<Notification> findAllByPrivilegeAndCreatedByNot(String privilege, User suer);
    @Query(nativeQuery = true, value = "SELECT * FROM Notification as e WHERE e.PRIVILEGE IN (:privileges) and e.CREATED_BY !=(:user)")
    // 3. Spring JPA In cause using native query
    List<Notification> findAllByPrivilegeAndCreatedByNot(@Param("privileges") List<String> privileges,
                                                         @Param("user") User user);
}
