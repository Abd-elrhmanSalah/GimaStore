package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class SupplyProcessPartDistDTO implements Serializable {

    private Long id;
    private SupplyProcessPart supplyProcessPart;
    private Store store;
    private Integer amount;
    private User actionBy;
    private User distBy;
    private Status status;
    private Date actionDate;
    private Date creationDate;
    private String notes;

}