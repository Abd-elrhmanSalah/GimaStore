package com.gima.gimastore.model.ReturnsProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.model.supplyProcess.StoreAmount;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReturnPartRequest implements Serializable {
    private Part part;
    private User createdBy;
    private Date creationDate = new Date();
    private List<StoreAmount> storeAmounts;

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<StoreAmount> getStoreAmounts() {
        return storeAmounts;
    }

    public void setStoreAmounts(List<StoreAmount> storeAmounts) {
        this.storeAmounts = storeAmounts;
    }
}
