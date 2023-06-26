package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "PRODUCT_PART")
public class ProductPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Part part;

    @Column(name = "AMOUNT")
    @NotNull
    private Integer amount;

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
