package com.gima.gimastore.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.gima.gimastore.service.UserService;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Roles")

public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "ROLENAME")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> userList;

    
    public Role() {
    	
    }
    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
