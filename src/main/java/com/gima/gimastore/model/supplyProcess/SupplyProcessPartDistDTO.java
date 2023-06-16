package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;

import java.io.Serializable;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplyProcessPart getSupplyProcessPart() {
        return supplyProcessPart;
    }

    public void setSupplyProcessPart(SupplyProcessPart supplyProcessPart) {
        this.supplyProcessPart = supplyProcessPart;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}