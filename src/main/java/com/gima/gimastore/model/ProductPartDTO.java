package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Product;

import java.io.Serializable;

public class ProductPartDTO implements Serializable {
    private Long id;
    private Part part;
    private Product product;
    private Integer amount;

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
