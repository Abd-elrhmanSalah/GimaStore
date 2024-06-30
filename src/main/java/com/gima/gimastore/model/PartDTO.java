package com.gima.gimastore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@RequiredArgsConstructor
public class PartDTO implements Serializable {

    private Long id;
    private String partName;
    private BigDecimal currentCost;
    private byte[] picture;
    private Boolean isLocked = false;
    private Integer minAmount;
    private Integer totalInStores;

   }
