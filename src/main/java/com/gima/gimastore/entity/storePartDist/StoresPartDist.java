package com.gima.gimastore.entity.storePartDist;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Status;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "STORE_PARTS_DIST")
public class StoresPartDist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID_FROM")
    private Store storeFrom;

    @ManyToOne
    @JoinColumn(name = "STORE_ID_TO")
    private Store storeTo;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "ACTION_BY")
    @Nullable
    private User actionBy;

    @ManyToOne
    @JoinColumn(name = "Dist_BY")
    private User distBy;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @Column(name = "ACTION_DATE")
    @Nullable
    private Date actionDate;

    @Column(name = "CREATION_DATE")
    private Date creationDate = new Date();

    @Column(name = "NOTES")
    @Nullable
    @Nationalized
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStoreFrom() {
        return storeFrom;
    }

    public void setStoreFrom(Store storeFrom) {
        this.storeFrom = storeFrom;
    }

    public Store getStoreTo() {
        return storeTo;
    }

    public void setStoreTo(Store storeTo) {
        this.storeTo = storeTo;
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

    public User getDistBy() {
        return distBy;
    }

    public void setDistBy(User distBy) {
        this.distBy = distBy;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public User getActionBy() {
        return actionBy;
    }

    public void setActionBy(User actionBy) {
        this.actionBy = actionBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Nullable
    public String getNotes() {
        return notes;
    }

    public void setNotes(@Nullable String notes) {
        this.notes = notes;
    }
}
