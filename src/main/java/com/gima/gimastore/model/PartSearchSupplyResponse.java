package com.gima.gimastore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class PartSearchSupplyResponse implements Serializable {

    private Long supplyProcessId;
    private String SupplierName;
    private String billId;
    private String creationDate;
    private String creationBy;
    private String amount;
    private String cost;
    private String remainAmount;
    private String distAmount;

}
