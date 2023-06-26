package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ProductionRequestDTO implements Serializable {

    private Long id;
    @NotNull
    private String requestID;

    private Product product;
    @NotNull
    private Department department;
    @NotNull
    private Supervisor supervisor;
    @NotNull
    @N
    private Integer expectedProduction;
    private Integer exactlyProduction;

    private User createdBy;

    private Date creationDate = new Date();
    private Boolean isProduct;

    private Boolean isLocked;

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

    public void setProduct(Boolean product) {
        isProduct = product;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
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

    public Integer getExactlyProduction() {
        return exactlyProduction;
    }

    public void setExactlyProduction(Integer exactlyProduction) {
        this.exactlyProduction = exactlyProduction;
    }
}
