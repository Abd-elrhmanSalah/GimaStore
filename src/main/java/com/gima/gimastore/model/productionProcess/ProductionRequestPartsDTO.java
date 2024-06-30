package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.productProcess.ProductionRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ProductionRequestPartsDTO implements Serializable {

    private Long id;
    private ProductionRequest productionRequest;
    private Part part;
    private Integer amount;
    private Integer returnedAmount;
    private Integer harmedAmount;
    private Integer unharmedAmount;
    private Boolean isHaveReturned;

}
