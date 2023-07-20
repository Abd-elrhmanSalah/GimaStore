package com.gima.gimastore.model;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ProductionStoreRequest implements Serializable {
    private Long id;
    private String requestID;
    private Product product;
    private Department department;
    private Supervisor supervisor;
    private Integer expectedProduction;
    private User createdBy;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getExpectedProduction() {
        return expectedProduction;
    }

    public void setExpectedProduction(Integer expectedProduction) {
        this.expectedProduction = expectedProduction;
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
}
