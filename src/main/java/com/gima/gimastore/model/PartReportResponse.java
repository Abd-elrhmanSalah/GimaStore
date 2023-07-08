package com.gima.gimastore.model;

import java.io.Serializable;

public class PartReportResponse implements Serializable {

    private String partName;
    private Integer existingAmount;
    private Integer outGoing;
    private Integer balanceFirstPeriod;
    private Integer harmedAmount;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(Integer existingAmount) {
        this.existingAmount = existingAmount;
    }

    public Integer getOutGoing() {
        return outGoing;
    }

    public void setOutGoing(Integer outGoing) {
        this.outGoing = outGoing;
    }

    public Integer getBalanceFirstPeriod() {
        return balanceFirstPeriod;
    }

    public void setBalanceFirstPeriod(Integer balanceFirstPeriod) {
        this.balanceFirstPeriod = balanceFirstPeriod;
    }

    public Integer getHarmedAmount() {
        return harmedAmount;
    }

    public void setHarmedAmount(Integer harmedAmount) {
        this.harmedAmount = harmedAmount;
    }
}
