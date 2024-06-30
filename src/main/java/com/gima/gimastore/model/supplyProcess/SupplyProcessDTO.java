package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.model.SupplierDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class SupplyProcessDTO implements Serializable {

    private Long id;
    private SupplierDTO supplier;
    private User createdBy;
    private Date creationDate = new Date();
    private String notes;
    private Boolean isLocked = false;
    private String billId;
    private Boolean isFullDist;
    private byte[] picture;

}
