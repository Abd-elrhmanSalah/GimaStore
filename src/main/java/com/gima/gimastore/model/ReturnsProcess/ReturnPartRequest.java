package com.gima.gimastore.model.ReturnsProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.model.supplyProcess.StoreAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ReturnPartRequest implements Serializable {

    private Part part;
    private User createdBy;
    private Date creationDate = new Date();
    private List<StoreAmount> storeAmounts;

}
