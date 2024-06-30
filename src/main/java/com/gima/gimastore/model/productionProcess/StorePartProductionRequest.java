package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class StorePartProductionRequest implements Serializable {

    private Long id;
    private Integer outedAmount;
    private User lastUpdatedBy;
    private Date lastUpdateDate = new Date();

}
