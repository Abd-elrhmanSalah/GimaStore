package com.gima.gimastore.model;

import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@RequiredArgsConstructor
public class StorePartSettlementRequest implements Serializable {

    private Store store;
    private User createdBy;
    private Date creationDate;
    private List<PartsSettlement> parts;

}
