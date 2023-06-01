package com.gima.gimastore.model;

import com.gima.gimastore.entity.SupplyProcess;

import java.io.Serializable;
import java.util.Set;

public class SupplyProcessRequest implements Serializable {
    private SupplyProcessDTO supplyProcess;
    private Set<PartRequest> partList;

    public SupplyProcessRequest(SupplyProcessDTO supplyProcess, Set<PartRequest> partList) {
        this.supplyProcess = supplyProcess;
        this.partList = partList;
    }

    public SupplyProcessDTO getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcessDTO supplyProcess) {
        this.supplyProcess = supplyProcess;
    }

    public Set<PartRequest> getPartList() {
        return partList;
    }

    public void setPartList(Set<PartRequest> partList) {
        this.partList = partList;
    }
}
