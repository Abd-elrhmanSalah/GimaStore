package com.gima.gimastore.model;

import com.gima.gimastore.entity.Store;

import java.io.Serializable;

public class StorePartRequest implements Serializable {

    private PartDTO part;
    private Integer amount;


    public StorePartRequest() {
    }

    public PartDTO getPart() {
        return part;
    }

    public void setPart(PartDTO part) {
        this.part = part;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
