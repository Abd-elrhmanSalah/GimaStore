package com.gima.gimastore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;
    private String roleName;
}
