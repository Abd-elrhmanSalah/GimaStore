package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Part;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.util.List;
@Data
@RequiredArgsConstructor
public class PartsReturnRequest {

    private Part part;
    private Integer amountReturn;
    private Integer amountIncoming;
    private List<StoreAmount> storeAmounts;

}
