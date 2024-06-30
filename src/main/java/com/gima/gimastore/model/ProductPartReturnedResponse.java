package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class ProductPartReturnedResponse implements Serializable {

    private Long id;
    private Part part;
    private Integer requestedAmount;
    private Integer returnedAmount;

}
