package com.gima.gimastore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gima.gimastore.entity.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
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

}
