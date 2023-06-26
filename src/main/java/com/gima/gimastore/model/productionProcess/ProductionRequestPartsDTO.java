package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.productProcess.ProductionRequest;

import java.io.Serializable;

public class ProductionRequestPartsDTO implements Serializable {

    private Long id;

    private ProductionRequest productionRequest;

    private Part part;

    private Integer amount;

    private Integer returnedAmount;

    private Integer harmedAmount;

    private Integer unharmedAmount;

    private Boolean isHaveReturned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionRequest getProductionRequest() {
        return productionRequest;
    }

    public void setProductionRequest(ProductionRequest productionRequest) {
        this.productionRequest = productionRequest;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(Integer returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    public Integer getHarmedAmount() {
        return harmedAmount;
    }

    public void setHarmedAmount(Integer harmedAmount) {
        this.harmedAmount = harmedAmount;
    }

    public Integer getUnharmedAmount() {
        return unharmedAmount;
    }

    public void setUnharmedAmount(Integer unharmedAmount) {
        this.unharmedAmount = unharmedAmount;
    }

    public Boolean getHaveReturned() {
        return isHaveReturned;
    }

    public void setHaveReturned(Boolean haveReturned) {
        isHaveReturned = haveReturned;
    }
}
