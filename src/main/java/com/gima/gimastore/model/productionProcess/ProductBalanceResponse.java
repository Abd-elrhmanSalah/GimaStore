package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.productProcess.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ProductBalanceResponse implements Serializable {

    private Product product;
    private Integer amountRequested;
    private Integer amountOut;

}
