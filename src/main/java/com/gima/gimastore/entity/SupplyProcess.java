package com.gima.gimastore.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "SUPPLY_PROCESS")
public class SupplyProcess implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID")
    @Nationalized
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Nationalized
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "NOTES")
    @Nationalized
    private String notes;


    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }
}
