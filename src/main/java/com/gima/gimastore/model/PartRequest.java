package com.gima.gimastore.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartRequest implements Serializable {
    private PartDTO part;
    private BigDecimal cost;
    private Integer amount;
    private Boolean isFullDist;
    private Boolean isPartialDist;

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

    public Boolean getFullDist() {
        return isFullDist;
    }

    public void setFullDist(Boolean fullDist) {
        isFullDist = fullDist;
    }

    public Boolean getPartialDist() {
        return isPartialDist;
    }

    public void setPartialDist(Boolean partialDist) {
        isPartialDist = partialDist;
    }
}
