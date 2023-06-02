package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplyProcessPartsDTO implements Serializable {

    private SupplyProcessDTO supplyProcess;
    private Part part;

    private BigDecimal cost;
    private Integer amount;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public SupplyProcessDTO getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcessDTO supplyProcess) {
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
