package com.gima.gimastore.model.productionProcess;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
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
    private Integer expectedProduction;
    private Integer exactlyProduction;
    private User createdBy;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date creationDate = new Date();
    private Boolean isLocked = false;
    private Boolean isCompleted = false;
    private Boolean isItProduct= false;


}
