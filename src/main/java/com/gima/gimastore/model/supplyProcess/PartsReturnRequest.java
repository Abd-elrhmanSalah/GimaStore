package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.Part;

import javax.persistence.Column;
import java.util.List;

public class PartsReturnRequest {
    private Part part;
    private Integer amountReturn;
    private Integer amountIncoming;
    private List<StoreAmount> storeAmounts;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getAmountReturn() {
        return amountReturn;
    }

    public void setAmountReturn(Integer amountReturn) {
        this.amountReturn = amountReturn;
    }

    public Integer getAmountIncoming() {
        return amountIncoming;
    }

    public void setAmountIncoming(Integer amountIncoming) {
        this.amountIncoming = amountIncoming;
    }

    public List<StoreAmount> getStoreAmounts() {
        return storeAmounts;
    }

    public void setStoreAmounts(List<StoreAmount> storeAmounts) {
        this.storeAmounts = storeAmounts;
    }
}
