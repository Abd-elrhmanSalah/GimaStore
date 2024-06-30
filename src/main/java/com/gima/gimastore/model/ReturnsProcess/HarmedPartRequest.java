package com.gima.gimastore.model.ReturnsProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.model.supplyProcess.StoreAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class HarmedPartRequest implements Serializable {

    private Part part;
    private User createdBy;
    private Date creationDate = new Date();
    private Integer amount;
    private String billId;

}
