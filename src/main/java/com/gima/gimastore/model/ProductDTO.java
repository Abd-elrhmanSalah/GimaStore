package com.gima.gimastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO implements Serializable {
    private Long id;
    private String productName;
    private byte[] picture;
    private Boolean isLocked;
    private List<PartRequest> parts=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public List<PartRequest> getParts() {
        return parts;
    }

    public void setParts(List<PartRequest> parts) {
        this.parts = parts;
    }
}
