package com.gima.gimastore.model.productionProcess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gima.gimastore.entity.Part;

import java.io.Serializable;

public class ProductionPartsAPIRequest implements Serializable {
    private Part part;

    private Integer amount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer returnedAmount = 0;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer harmedAmount = 0;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer unharmedAmount = 0;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isHaveReturned = false;

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

    public Integer getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(Integer returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    public Integer getHarmedAmount() {
        return harmedAmount;
    }

    public void setHarmedAmount(Integer harmedAmount) {
        this.harmedAmount = harmedAmount;
    }

    public Integer getUnharmedAmount() {
        return unharmedAmount;
    }

    public void setUnharmedAmount(Integer unharmedAmount) {
        this.unharmedAmount = unharmedAmount;
    }

    public Boolean getHaveReturned() {
        return isHaveReturned;
    }

    public void setHaveReturned(Boolean haveReturned) {
        isHaveReturned = haveReturned;
    }
}
