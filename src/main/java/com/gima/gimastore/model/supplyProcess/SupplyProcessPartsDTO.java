package com.gima.gimastore.model.supplyProcess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupplyProcessPartsDTO implements Serializable {

    private SupplyProcessDTO supplyProcess;
    private List<SupplyProcessPartRequest> parts = new ArrayList<>();


    public SupplyProcessDTO getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcessDTO supplyProcess) {
        this.supplyProcess = supplyProcess;
    }

    public List<SupplyProcessPartRequest> getParts() {
        return parts;
    }

    public void setParts(List<SupplyProcessPartRequest> parts) {
        this.parts = parts;
    }
}
