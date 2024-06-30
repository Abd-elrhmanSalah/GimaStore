package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.model.PartDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class SupplyProcessPartRequest implements Serializable {

    private Long id;
    private PartDTO part;
    private BigDecimal cost;
    private Integer amount;
    private Integer amountAfterReturn;
    private Integer distAmount = 0;
    private Integer remainAmount = 0;
    private Boolean isFullDist;
    private Boolean isPartialDist;
    
}
