package com.gima.gimastore.model;

import com.gima.gimastore.entity.Role;
import com.sun.istack.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.io.Serializable;


public class UserDTO implements Serializable {

    private Long id;
    @NotNull
    private String userName;
    @NotNull
    @Size(min = 6, message = "كلمة السر لا يجب ان تقل عن 6")
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private Role role;
    private MultipartFile file;
    private byte[] avatar;

    public UserDTO() {
    }

    public UserDTO(Long id, String userName, String password, String firstName, String lastName, Role role, byte[] avatar) {
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
