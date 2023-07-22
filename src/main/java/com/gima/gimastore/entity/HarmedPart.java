package com.gima.gimastore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HARMED_PART")
public class HarmedPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;
    @Column(name = "HARMED_AMOUNT")
    private Integer amountHarmed;
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

    public Integer getAmountHarmed() {
        return amountHarmed;
    }

    public void setAmountHarmed(Integer amountHarmed) {
        this.amountHarmed = amountHarmed;
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
