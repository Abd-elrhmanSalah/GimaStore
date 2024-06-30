package com.gima.gimastore.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "SUPPLIER")
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "SUPPLIER_NAME")
    @Nationalized
    private String supplierName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NOTES")
    @Nationalized
    private String notes;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    private Boolean isLocked=false;

    public Supplier() {
    }

    public Supplier(Long id) {
        this.id = id;
    }

}
