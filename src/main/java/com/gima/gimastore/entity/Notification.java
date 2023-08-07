package com.gima.gimastore.entity;

import com.gima.gimastore.entity.User;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "TITLE")
    @Nationalized
    @NotNull
    private String title;
    @Column(name = "MESSAGE")
    @Nationalized
    @NotNull
    private String message;

    @Column(name = "PRIVILEGE")
    @Nationalized
    @NotNull
    private String privilege;

    @Column(name = "READBY")
    @Nationalized
    @NotNull
    private String readBy;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "ROUTE_NAME")
    private String routeName;
    @Column(name = "RECEIVER")
    @Nullable
    private Long receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getReadBy() {
        return readBy;
    }

    public void setReadBy(String readBy) {
        this.readBy = readBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }
}
