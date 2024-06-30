package com.gima.gimastore.model.supplyProcess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class SupplyProcessWithPartsResponse implements Serializable {

    private final SupplyProcessPartsDTO supplyProcessParts;

}
