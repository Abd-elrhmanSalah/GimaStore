package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import com.gima.gimastore.model.supplyProcess.StoreRequest;

import java.io.Serializable;
import java.util.List;

public class SupplyProcessPartDistDTO implements Serializable {
    private SupplyProcessPart supplyProcessPart;
    private List<StoreRequest> storeRequests;

    public SupplyProcessPart getSupplyProcessPart() {
        return supplyProcessPart;
    }

    public void setSupplyProcessPart(SupplyProcessPart supplyProcessPart) {
        this.supplyProcessPart = supplyProcessPart;
    }

    public List<StoreRequest> getStoreRequests() {
        return storeRequests;
    }

    public void setStoreRequests(List<StoreRequest> storeRequests) {
        this.storeRequests = storeRequests;
    }
}
