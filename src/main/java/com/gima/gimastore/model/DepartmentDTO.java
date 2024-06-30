package com.gima.gimastore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class DepartmentDTO implements Serializable {

    private Long id;
    private String deptName;
    private Boolean isLocked;

}
