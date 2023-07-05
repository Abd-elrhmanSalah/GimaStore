package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class ProductPartReturnedResponse implements Serializable {
    private Long id;
    private Part part;
    private Integer requestedAmount;
    private Integer returnedAmount;

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

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(Integer returnedAmount) {
        this.returnedAmount = returnedAmount;
    }
}
