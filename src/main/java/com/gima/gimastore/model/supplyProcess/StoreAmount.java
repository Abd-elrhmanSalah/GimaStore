package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Store;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class StoreAmount implements Serializable {

    private Store store;
    private Integer amount;

}
