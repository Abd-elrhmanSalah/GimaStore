package com.gima.gimastore.entity.productProcess;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
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

}
