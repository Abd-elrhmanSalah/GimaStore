package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class PartRequest implements Serializable {
    private Part part;
    private Integer amount;

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
}
