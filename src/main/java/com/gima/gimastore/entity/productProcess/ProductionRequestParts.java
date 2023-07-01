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

    @Column(name = "AMOUNT")
    @NotNull
    private Integer amount;

    @Column(name = "RETURNED_AMOUNT")
    @NotNull
    private Integer returnedAmount;

    @Column(name = "HARMED_AMOUNT")
    @NotNull
    private Integer harmedAmount;

    @Column(name = "UNHARMED_AMOUNT")
    @NotNull
    private Integer unharmedAmount;

    @Column(name = "IS_HAVE_RETURNED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isHaveReturned = false;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(Integer returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    public Integer getHarmedAmount() {
        return harmedAmount;
    }

    public void setHarmedAmount(Integer harmedAmount) {
        this.harmedAmount = harmedAmount;
    }

    public Integer getUnharmedAmount() {
        return unharmedAmount;
    }

    public void setUnharmedAmount(Integer unharmedAmount) {
        this.unharmedAmount = unharmedAmount;
    }

    public Boolean getHaveReturned() {
        return isHaveReturned;
    }

    public void setHaveReturned(Boolean haveReturned) {
        isHaveReturned = haveReturned;
    }
}
