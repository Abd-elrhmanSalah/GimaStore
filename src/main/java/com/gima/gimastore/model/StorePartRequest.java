package com.gima.gimastore.model;

import com.gima.gimastore.entity.Store;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class StorePartRequest implements Serializable {

    private PartDTO part;
    private Integer amount;

}
