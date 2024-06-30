package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
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

}
