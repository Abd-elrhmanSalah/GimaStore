package com.gima.gimastore.entity.productProcess;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCT_OUT_PRODUCTS")
public class ProductOutProducts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Product product;

    @Column(name = "AMOUNT")
    @NotNull
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_OUT_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private ProductOut productOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductOut getProductOut() {
        return productOut;
    }

    public void setProductOut(ProductOut productOut) {
        this.productOut = productOut;
    }
}
