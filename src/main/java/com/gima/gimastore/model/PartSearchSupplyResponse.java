package com.gima.gimastore.model;

import java.io.Serializable;

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

    public Long getSupplyProcessId() {
        return supplyProcessId;
    }

    public void setSupplyProcessId(Long supplyProcessId) {
        this.supplyProcessId = supplyProcessId;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(String creationBy) {
        this.creationBy = creationBy;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getDistAmount() {
        return distAmount;
    }

    public void setDistAmount(String distAmount) {
        this.distAmount = distAmount;
    }
}
