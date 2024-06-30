package com.gima.gimastore.model;

import com.gima.gimastore.entity.Part;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@RequiredArgsConstructor
public class PartAmount implements Serializable {

    private Part part;
    private Integer totalAmount;

}
