package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

public class StorePartSettlementDTO implements Serializable {
    private Long id;
    private Part part;
    private Store store;
    private Integer amountPrevious;
    private Integer amountUpdate;
    private Integer amountDiff;
    private User createdBy;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getAmountPrevious() {
        return amountPrevious;
    }

    public void setAmountPrevious(Integer amountPrevious) {
        this.amountPrevious = amountPrevious;
    }

    public Integer getAmountUpdate() {
        return amountUpdate;
    }

    public void setAmountUpdate(Integer amountUpdate) {
        this.amountUpdate = amountUpdate;
    }

    public Integer getAmountDiff() {
        return amountDiff;
    }

    public void setAmountDiff(Integer amountDiff) {
        this.amountDiff = amountDiff;
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
}
