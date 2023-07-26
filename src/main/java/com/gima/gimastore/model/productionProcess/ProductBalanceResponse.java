package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.productProcess.Product;

import java.io.Serializable;

public class ProductBalanceResponse implements Serializable {
    private Product product;
    private Integer amountRequested;
    private Integer amountOut;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(Integer amountRequested) {
        this.amountRequested = amountRequested;
    }

    public Integer getAmountOut() {
        return amountOut;
    }

    public void setAmountOut(Integer amountOut) {
        this.amountOut = amountOut;
    }
}
