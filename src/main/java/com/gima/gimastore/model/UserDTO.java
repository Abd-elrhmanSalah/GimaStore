package com.gima.gimastore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gima.gimastore.entity.Role;

import javax.validation.constraints.Size;
import java.io.Serializable;


public class UserDTO implements Serializable {

    private Long id;

    private String userName;

    @Size(min = 6, message = "كلمة السر لا يجب ان تقل عن 6")
    private String password;

    private String firstName;

    private String lastName;
    private Role role;

    private byte[] avatar;
    @JsonIgnore
    @JsonDeserialize
    private Boolean changePassword;

    @JsonIgnore
    @JsonDeserialize
    private String oldPassword;

    private Boolean isLocked = false;
    private Long storeId;

    private UserPrivilegesDTO userPrivileges;

    public UserDTO() {
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

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public UserPrivilegesDTO getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(UserPrivilegesDTO userPrivileges) {
        this.userPrivileges = userPrivileges;
    }
}
