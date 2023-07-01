package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class ProductPartResponse implements Serializable {
    private Part part;
    private Integer requestedAmount;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
}
