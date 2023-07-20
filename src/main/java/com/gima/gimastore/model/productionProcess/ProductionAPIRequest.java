package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.model.supplyProcess.StoreAmount;

import java.io.Serializable;
import java.util.List;

public class ProductionAPIRequest implements Serializable {
    private ProductionRequestDTO productionRequestDTO;
    private List<ProductionPartsAPIRequest> parts;


    public ProductionRequestDTO getProductionRequestDTO() {
        return productionRequestDTO;
    }

    public void setProductionRequestDTO(ProductionRequestDTO productionRequestDTO) {
        this.productionRequestDTO = productionRequestDTO;
    }

    public List<ProductionPartsAPIRequest> getParts() {
        return parts;
    }

    public void setParts(List<ProductionPartsAPIRequest> parts) {
        this.parts = parts;
    }

}
