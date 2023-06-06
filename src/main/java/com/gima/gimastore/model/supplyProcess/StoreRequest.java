package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Store;

import java.io.Serializable;

public class StoreRequest implements Serializable {
    private Store store;
    private Integer amount;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
