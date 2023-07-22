package com.gima.gimastore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RETURNED_PART")
public class ReturnedPart implements Serializable {
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
    @Column(name = "RETURN_AMOUNT")
    private Integer amountReturned;
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

    public Integer getAmountReturned() {
        return amountReturned;
    }

    public void setAmountReturned(Integer amountReturned) {
        this.amountReturned = amountReturned;
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
