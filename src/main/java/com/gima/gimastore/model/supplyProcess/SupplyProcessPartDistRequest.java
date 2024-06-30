package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@RequiredArgsConstructor
public class SupplyProcessPartDistRequest implements Serializable {

    private SupplyProcessPart supplyProcessPart;
    private List<StoreRequest> storeRequests;
    private Long statusId;
    private Long distUserId;

}
