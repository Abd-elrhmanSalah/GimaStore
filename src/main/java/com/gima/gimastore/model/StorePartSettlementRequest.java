package com.gima.gimastore.model;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StorePartSettlementRequest implements Serializable {
    private Store store;
    private User createdBy;
    private Date creationDate;
    private List<PartsSettlement> parts;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public List<PartsSettlement> getParts() {
        return parts;
    }

    public void setParts(List<PartsSettlement> parts) {
        this.parts = parts;
    }
}
