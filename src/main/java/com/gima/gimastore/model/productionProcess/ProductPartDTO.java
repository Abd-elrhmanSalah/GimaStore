package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.productProcess.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ProductPartDTO implements Serializable {

    private Long id;
    private Part part;
    private Product product;
    private Integer amount;

}
