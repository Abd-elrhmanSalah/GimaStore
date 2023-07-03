package com.gima.gimastore.model.productionProcess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gima.gimastore.entity.Part;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class ProductionPartsAPIRequest implements Serializable {
    @Nullable
    private Long id;
    private Part part;
    private Integer requestedAmount;

    private Integer usedAmount = 0;

    private Integer returnedAmount = 0;

    private Integer harmedAmount = 0;

    private Integer unharmedAmount = 0;

    private Boolean isHaveReturned = false;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Integer usedAmount) {
        this.usedAmount = usedAmount;
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
