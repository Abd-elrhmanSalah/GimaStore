package com.gima.gimastore.model.productionProcess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductionAPIRequest implements Serializable {

    private ProductionRequestDTO productionRequestDTO;
    private List<ProductionPartsAPIRequest> parts;

}
