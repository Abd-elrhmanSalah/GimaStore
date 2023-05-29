package com.gima.gimastore.entity;


import lombok.Builder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    @Nationalized
    private String userName;

    @Column(name = "PASSWORD")
    @Nationalized
    private String password;

    @Column(name = "FIRST_NAME")
    @Nationalized
    private String firstName;

    @Column(name = "LAST_NAME")
    @Nationalized
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Role role;

    @Lob
    @Column(name = "AVATAR", length = 1000)
    private byte[] avatar;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String userName, String password, String firstName, String lastName, Role role, byte[] avatar) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
