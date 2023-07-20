package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PRODUCT_REQUEST_PARTS")
public class ProductionRequestParts implements Serializable {
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

    @Column(name = "REQUESTED_AMOUNT")
    @NotNull
    private Integer requestedAmount;


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

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
}
