package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class PartAmount implements Serializable {
    private Part part;
    private Integer totalAmount;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}
