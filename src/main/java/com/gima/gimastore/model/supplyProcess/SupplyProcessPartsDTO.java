package com.gima.gimastore.model.supplyProcess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@RequiredArgsConstructor
public class SupplyProcessPartsDTO implements Serializable {

    private SupplyProcessDTO supplyProcess;
    private List<SupplyProcessPartRequest> parts = new ArrayList<>();

}
