package com.gima.gimastore.model.supplyProcess;

import java.io.Serializable;

public class SupplyProcessWithPartsResponse implements Serializable {
    SupplyProcessPartsDTO supplyProcessParts;

    public SupplyProcessWithPartsResponse(SupplyProcessPartsDTO supplyProcessPartsDTO) {
        this.supplyProcessParts = supplyProcessPartsDTO;
    }

    public SupplyProcessPartsDTO getSupplyProcessParts() {
        return supplyProcessParts;
    }

    public void setSupplyProcessParts(SupplyProcessPartsDTO supplyProcessParts) {
        this.supplyProcessParts = supplyProcessParts;
    }
}
