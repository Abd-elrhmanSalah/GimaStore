package com.gima.gimastore.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
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
    @JoinColumn(name = "ROLE_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Role role;

    @Lob
    @Column(name = "AVATAR", length = 1000)
    private byte[] avatar;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    private Boolean isLocked=false;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }
}
