package com.gima.gimastore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;
@Data
@RequiredArgsConstructor
public class StoresPartsDistRequest implements Serializable {

    private Long id;
    private Long storeFromId;
    private Long storeToId;
    private Set<StorePartRequest> parts;
    private Long statusId;
    private Long distUserId;
    private String notes;

}
