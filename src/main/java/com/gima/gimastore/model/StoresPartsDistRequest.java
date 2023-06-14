package com.gima.gimastore.model;

import java.io.Serializable;
import java.util.Set;

public class StoresPartsDistRequest implements Serializable {

    private Long id;
    private Long storeFromId;
    private Long storeToId;
    private Set<StorePartRequest> parts;
    private Long statusId;
    private Long distUserId;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreFromId() {
        return storeFromId;
    }

    public void setStoreFromId(Long storeFromId) {
        this.storeFromId = storeFromId;
    }

    public Long getStoreToId() {
        return storeToId;
    }

    public void setStoreToId(Long storeToId) {
        this.storeToId = storeToId;
    }

    public Set<StorePartRequest> getParts() {
        return parts;
    }

    public void setParts(Set<StorePartRequest> parts) {
        this.parts = parts;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getDistUserId() {
        return distUserId;
    }

    public void setDistUserId(Long distUserId) {
        this.distUserId = distUserId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
