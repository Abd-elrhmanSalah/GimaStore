package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class ProductPartResponse implements Serializable {
    @Nullable
    private Long id;
    private Part part;
    private Integer requestedAmount;

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
}
