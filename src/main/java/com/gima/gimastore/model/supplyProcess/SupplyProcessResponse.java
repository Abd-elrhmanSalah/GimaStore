package com.gima.gimastore.model.supplyProcess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupplyProcessResponse implements Serializable {

    private List<SupplyProcessDTO> supplyProcess = new ArrayList<>();

    public List<SupplyProcessDTO> getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(List<SupplyProcessDTO> supplyProcess) {
        this.supplyProcess = supplyProcess;
    }
}
