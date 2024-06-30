package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SupplyProcessPartsReturnsRequest implements Serializable {

    private SupplyProcess supplyProcess;
    private User createdBy;
    private Date creationDate;
    private List<PartsReturnRequest> parts;

}
