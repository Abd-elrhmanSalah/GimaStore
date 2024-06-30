package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "PRODUCT_REQUEST")
public class ProductionRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "REQUEST_ID")
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
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;
    @Column(name = "IS_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isItProduct = false;

    @Column(name = "IS_FULL_OUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean isFullOut = false;

    @Column(name = "IS_COMPLETED", columnDefinition = "BIT DEFAULT 0")
    private Boolean isCompleted = false;

}
