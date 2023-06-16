package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;

public class StorePartDistDTO implements Serializable {

    private Long id;

    private Store storeFrom;

    private Store storeTo;

    private Part part;

    private Integer amount;

    private User actionBy;

    private User distBy;

    private Status status;

    private Date actionDate;

    private Date creationDate;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStoreFrom() {
        return storeFrom;
    }

    public void setStoreFrom(Store storeFrom) {
        this.storeFrom = storeFrom;
    }

    public Store getStoreTo() {
        return storeTo;
    }

    public void setStoreTo(Store storeTo) {
        this.storeTo = storeTo;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getDistBy() {
        return distBy;
    }

    public void setDistBy(User distBy) {
        this.distBy = distBy;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public User getActionBy() {
        return actionBy;
    }

    public void setActionBy(User actionBy) {
        this.actionBy = actionBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Nullable
    public String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }
}
