package com.gima.gimastore.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLIER")
public class Supplier {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
