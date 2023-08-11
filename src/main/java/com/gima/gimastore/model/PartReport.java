package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class PartReport implements Serializable {
    private Part part;
    private Integer totalSuppliesIncome;
    private Integer totalSuppliesReturns;
    private Integer totalOut;
    private Integer totalProductionRequested;
    private Integer totalReturns;
    private Integer totalHarmed;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getTotalSuppliesIncome() {
        return totalSuppliesIncome;
    }

    public void setTotalSuppliesIncome(Integer totalSuppliesIncome) {
        this.totalSuppliesIncome = totalSuppliesIncome;
    }

    public Integer getTotalSuppliesReturns() {
        return totalSuppliesReturns;
    }

    public Integer getTotalProductionRequested() {
        return totalProductionRequested;
    }


    public void setTotalProductionRequested(Integer totalProductionRequested) {
        this.totalProductionRequested = totalProductionRequested;
    }

    public void setTotalSuppliesReturns(Integer totalSuppliesReturns) {
        this.totalSuppliesReturns = totalSuppliesReturns;
    }

    public Integer getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(Integer totalOut) {
        this.totalOut = totalOut;
    }

    public Integer getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(Integer totalReturns) {
        this.totalReturns = totalReturns;
    }

    public Integer getTotalHarmed() {
        return totalHarmed;
    }

    public void setTotalHarmed(Integer totalHarmed) {
        this.totalHarmed = totalHarmed;
    }
}
