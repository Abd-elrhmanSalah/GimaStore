package com.gima.gimastore.model.supplyProcess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;
@Data
@RequiredArgsConstructor
public class SupplyProcessRequest implements Serializable {

    private SupplyProcessDTO supplyProcessDTO;
    private Set<SupplyProcessPartRequest> partList;

}
