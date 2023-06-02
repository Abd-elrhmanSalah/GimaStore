package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartRequest implements Serializable {
    private PartDTO part;
    private BigDecimal cost;
    private Integer amount;
private Boolean isDist;
    public PartRequest() {
    }

    public PartRequest(PartDTO part, BigDecimal cost, Integer amount) {
        this.part = part;
        this.cost = cost;
        this.amount = amount;
    }

    public PartDTO getPart() {
        return part;
    }

    public void setPart(PartDTO part) {
        this.part = part;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getDist() {
        return isDist;
    }

    public void setDist(Boolean dist) {
        isDist = dist;
    }
}
