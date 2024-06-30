package com.gima.gimastore.model;

import com.gima.gimastore.entity.Department;
import com.gima.gimastore.entity.Supervisor;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Data
@RequiredArgsConstructor
public class ProductionStoreRequest implements Serializable {

    private Long id;
    private String requestID;
    private Product product;
    private Department department;
    private Supervisor supervisor;
    private Integer expectedProduction;
    private User createdBy;
    private Date creationDate;

}
