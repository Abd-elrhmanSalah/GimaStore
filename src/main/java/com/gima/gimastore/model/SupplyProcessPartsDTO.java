package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.SupplyProcess;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplyProcessPartsDTO implements Serializable {

    private Long id;
    private Part part;
    private SupplyProcess supplyProcess;
    private BigDecimal cost;
    private Integer amount;

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

    public SupplyProcess getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcess supplyProcess) {
        this.supplyProcess = supplyProcess;
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
}
