package com.gima.gimastore.model.productionProcess;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductionReturnRequest implements Serializable {

    @NotNull
    private String requestID;
    @NotNull
    private Integer exactlyProduction;
    private List<ProductionPartsAPIRequest> parts;

}
