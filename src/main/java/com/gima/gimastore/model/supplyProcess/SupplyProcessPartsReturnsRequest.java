package com.gima.gimastore.model.supplyProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SupplyProcessPartsReturnsRequest implements Serializable {
    private SupplyProcess supplyProcess;
    private User createdBy;
    private Date creationDate;
    private List<PartsReturnRequest> parts;

    public SupplyProcess getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcess supplyProcess) {
        this.supplyProcess = supplyProcess;
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

    public List<PartsReturnRequest> getParts() {
        return parts;
    }

    public void setParts(List<PartsReturnRequest> parts) {
        this.parts = parts;
    }
}
