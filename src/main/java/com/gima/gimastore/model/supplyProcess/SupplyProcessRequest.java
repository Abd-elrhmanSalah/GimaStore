package com.gima.gimastore.model.supplyProcess;

import java.io.Serializable;
import java.util.Set;

public class SupplyProcessRequest implements Serializable {
    private SupplyProcessDTO supplyProcessDTO;
    private Set<SupplyProcessPartRequest> partList;

    public SupplyProcessRequest(SupplyProcessDTO supplyProcessDTO, Set<SupplyProcessPartRequest> partList) {
        this.supplyProcessDTO = supplyProcessDTO;
        this.partList = partList;
    }

    public SupplyProcessDTO getSupplyProcessDTO() {
        return supplyProcessDTO;
    }

    public void setSupplyProcessDTO(SupplyProcessDTO supplyProcessDTO) {
        this.supplyProcessDTO = supplyProcessDTO;
    }

    public Set<SupplyProcessPartRequest> getPartList() {
        return partList;
    }

    public void setPartList(Set<SupplyProcessPartRequest> partList) {
        this.partList = partList;
    }
}
