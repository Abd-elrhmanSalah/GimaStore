package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.User;

import java.io.Serializable;
import java.util.Date;

public class StorePartProductionRequest implements Serializable {
    private Long id;
    private Integer outedAmount;
    private User lastUpdatedBy;
    private Date lastUpdateDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOutedAmount() {
        return outedAmount;
    }

    public void setOutedAmount(Integer outedAmount) {
        this.outedAmount = outedAmount;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
