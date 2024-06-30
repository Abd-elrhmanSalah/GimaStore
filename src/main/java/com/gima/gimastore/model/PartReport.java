package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class PartReport implements Serializable {

    private Part part;
    private Integer totalSuppliesIncome;
    private Integer totalSuppliesReturns;
    private Integer totalOut;
    private Integer totalProductionRequested;
    private Integer totalReturns;
    private Integer totalHarmed;

}
