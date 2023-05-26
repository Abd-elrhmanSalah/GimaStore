package com.gima.gimastore.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private Long roleId;
}
