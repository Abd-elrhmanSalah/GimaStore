package com.gima.gimastore.model.productionProcess;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gima.gimastore.entity.Part;
import com.gima.gimastore.model.supplyProcess.StoreAmount;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

public class ProductionPartsAPIRequest implements Serializable {
    @Nullable
    private Long id;
    private Part part;
    private Integer requestedAmount;
    private List<StoreAmount> storeAmounts;

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

    public List<StoreAmount> getStoreAmounts() {
        return storeAmounts;
    }

    public void setStoreAmounts(List<StoreAmount> storeAmounts) {
        this.storeAmounts = storeAmounts;
    }

}
