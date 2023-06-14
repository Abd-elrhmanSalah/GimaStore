package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.model.PartDTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplyProcessPartRequest implements Serializable {
    private Long id;
    private PartDTO part;
    private BigDecimal cost;
    private Integer amount;
    private Integer distAmount = 0;
    private Integer remainAmount = 0;
    private Boolean isFullDist;
    private Boolean isPartialDist;

    public SupplyProcessPartRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getDistAmount() {
        return distAmount;
    }

    public void setDistAmount(Integer distAmount) {
        this.distAmount = distAmount;
    }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
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
