package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.model.SupplierDTO;

import java.io.Serializable;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Boolean getFullDist() {
        return isFullDist;
    }

    public void setFullDist(Boolean fullDist) {
        isFullDist = fullDist;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
