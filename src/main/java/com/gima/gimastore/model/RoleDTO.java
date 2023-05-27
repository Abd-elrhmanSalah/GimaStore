package com.gima.gimastore.model;

import java.io.Serializable;


public class RoleDTO implements Serializable {
    private Long id;
    private String roleName;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
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
}
