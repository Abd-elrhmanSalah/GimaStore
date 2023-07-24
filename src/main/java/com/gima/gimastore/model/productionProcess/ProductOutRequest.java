package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductOutRequest implements Serializable {
    private Long id;
    private String destinationName;
    private String driverName;
    private String note;
    private User createdBy;
    private Date creationDate = new Date();
    private List<ProductAmount> productAmounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public List<ProductAmount> getProductAmounts() {
        return productAmounts;
    }

    public void setProductAmounts(List<ProductAmount> productAmounts) {
        this.productAmounts = productAmounts;
    }
}
