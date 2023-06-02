package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.model.PartRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupplyProcessPartsDTO implements Serializable {

    private SupplyProcessDTO supplyProcess;
    private List<PartRequest> parts = new ArrayList<>();


    public SupplyProcessDTO getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcessDTO supplyProcess) {
        this.supplyProcess = supplyProcess;
    }

    public List<PartRequest> getParts() {
        return parts;
    }

    public void setParts(List<PartRequest> parts) {
        this.parts = parts;
    }
}
