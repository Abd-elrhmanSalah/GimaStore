package com.gima.gimastore.entity.productProcess;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "PRODUCT")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PRODUCT_NAME")
    @Nationalized
    @NotNull
    private String productName;

    @Column(name = "PRICE")
    @NotNull
    private Double price;

    @Lob
    @Column(name = "PICTURE", length = 1000)
    @Nullable
    private byte[] picture;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked = false;
}
