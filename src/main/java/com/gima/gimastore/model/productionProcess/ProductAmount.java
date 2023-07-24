package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.productProcess.Product;

import java.io.Serializable;

public class ProductAmount implements Serializable {
    private Product product;
    private Integer amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
