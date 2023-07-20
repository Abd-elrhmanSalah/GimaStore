package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRODUCTION_PARTS_STORE_REQUEST")
public class ProductionPartsStoreRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_REQUEST_ID")
    @Nullable
    private ProductionRequest productionRequest;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    @Nullable
    private Part part;
    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
    @Column(name = "REQUESTED_AMOUNT")
    @NotNull
    private Integer requestedAmount;
    @Column(name = "OUTED_AMOUNT")
    @NotNull
    private Integer outedAmount;
    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdateDate;
    @Column(name = "IS_FULL_OUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean isFullOut = false;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public ProductionRequest getProductionRequest() {
        return productionRequest;
    }

    public void setProductionRequest(@Nullable ProductionRequest productionRequest) {
        this.productionRequest = productionRequest;
    }

    @Nullable
    public Part getPart() {
        return part;
    }

    public void setPart(@Nullable Part part) {
        this.part = part;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
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

    public Boolean getFullOut() {
        return isFullOut;
    }

    public void setFullOut(Boolean fullOut) {
        isFullOut = fullOut;
    }
}
