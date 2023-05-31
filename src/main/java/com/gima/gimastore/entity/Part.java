package com.gima.gimastore.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PART")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PART_NAME")
    @Nationalized
    private String partName;

    @Column(name = "CURRENT_COST")
    private BigDecimal currentCost;

    @Lob
    @Column(name = "PICTURE", length = 1000)
    private byte[] picture;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(BigDecimal currentAmount) {
        this.currentCost = currentAmount;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }
}
