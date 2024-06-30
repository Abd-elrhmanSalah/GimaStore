package com.gima.gimastore.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class PartReportResponse implements Serializable {

    private String partName;
    private Integer existingAmount;
    private Integer outGoing;
    private Integer balanceFirstPeriod;
    private Integer harmedAmount;

}
