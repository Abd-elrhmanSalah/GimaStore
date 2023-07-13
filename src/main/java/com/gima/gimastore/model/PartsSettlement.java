package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class PartsSettlement implements Serializable {
    private Part part;
    private Integer amountPrevious;
    private Integer amountUpdate;
    private Integer amountDiff;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
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
}
