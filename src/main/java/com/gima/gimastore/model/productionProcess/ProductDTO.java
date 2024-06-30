package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.model.PartRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductDTO implements Serializable {

    private Long id;
    private String productName;
    private byte[] picture;
    private Boolean isLocked;
    private Double price;
    private List<PartRequest> parts = new ArrayList<>();

}
