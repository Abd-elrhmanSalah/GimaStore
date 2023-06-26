package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_REQUEST")
public class ProductionRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PRICE")
    @NotNull
    private String requestID;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @Nullable
    private Product product;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "SUPERVISOR_ID")
    private Supervisor supervisor;

    @Column(name = "EXPECTED_PRODUCTION")
    @NotNull
    private Integer expectedProduction;

    @Column(name = "EXACTLY_PRODUCTION")
    @Nullable
    private Integer exactlyProduction;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Nationalized
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "IS_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isProduct = false;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked = false;

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

    @Nullable
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

    public void setProduct(@Nullable Product product) {
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

    @Nullable
    public Integer getExactlyProduction() {
        return exactlyProduction;
    }

    public void setExactlyProduction(@Nullable Integer exactlyProduction) {
        this.exactlyProduction = exactlyProduction;
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
