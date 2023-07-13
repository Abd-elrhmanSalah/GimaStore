package com.gima.gimastore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "STORE_PART_SETTLEMENT")
public class StorePartSettlement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;
    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
    @Column(name = "PREVIOUS_AMOUNT")
    private Integer amountPrevious;
    @Column(name = "UPDATED_AMOUNT")
    private Integer amountUpdate;
    @Column(name = "DIFF_AMOUNT")
    private Integer amountDiff;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;
    @Column(name = "CREATION_DATE")
    private Date creationDate;

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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getAmountPrevious() {
        return amountPrevious;
    }

    public void setAmountPrevious(Integer amountPrevious) {
        this.amountPrevious = amountPrevious;
    }

    public Integer getAmountUpdate() {
        return amountUpdate;
    }

    public void setAmountUpdate(Integer amountUpdate) {
        this.amountUpdate = amountUpdate;
    }

    public Integer getAmountDiff() {
        return amountDiff;
    }

    public void setAmountDiff(Integer amountDiff) {
        this.amountDiff = amountDiff;
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
}
