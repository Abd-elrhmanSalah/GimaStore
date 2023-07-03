package com.gima.gimastore.model.productionProcess;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ProductionReturnRequest implements Serializable {
    @NotNull
    private String requestID;
    @NotNull
    private Integer exactlyProduction;
    private List<ProductionPartsAPIRequest> parts;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Integer getExactlyProduction() {
        return exactlyProduction;
    }

    public void setExactlyProduction(Integer exactlyProduction) {
        this.exactlyProduction = exactlyProduction;
    }

    public List<ProductionPartsAPIRequest> getParts() {
        return parts;
    }

    public void setParts(List<ProductionPartsAPIRequest> parts) {
        this.parts = parts;
    }
}
