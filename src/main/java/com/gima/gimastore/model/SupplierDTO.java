package com.gima.gimastore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class SupplierDTO implements Serializable {

    private Long id;
    private String supplierName;
    private String phone;
    private String notes;
    private Boolean isLocked;

}
