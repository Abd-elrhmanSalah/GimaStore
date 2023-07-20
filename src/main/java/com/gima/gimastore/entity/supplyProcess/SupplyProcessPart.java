package com.gima.gimastore.entity.supplyProcess;

import com.gima.gimastore.entity.Part;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "SUPPLY_PROCESS_PARTS")
public class SupplyProcessPart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;

    @ManyToOne
    @JoinColumn(name = "SUPPLY_PROCESS_ID")
    private SupplyProcess supplyProcess;

    @Column(name = "COST")
    private BigDecimal cost;

    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "AFTER_RETURN_AMOUNT")
    private Integer amountAfterReturn;
    @Column(name = "DIST_AMOUNT")
    private Integer distAmount;

    @Column(name = "REMAIN_AMOUNT")
    private Integer remainAmount;
    @Column(name = "IS_FULL_DIST", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isFullDist = false;

    @Column(name = "IS_PARTIAL_DIST", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isPartialDist = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public SupplyProcess getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcess supplyProcess) {
        this.supplyProcess = supplyProcess;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDistAmount() {
        return distAmount;
    }

    public void setDistAmount(Integer distAmount) {
        this.distAmount = distAmount;
    }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Boolean getFullDist() {
        return isFullDist;
    }

    public void setFullDist(Boolean fullDist) {
        isFullDist = fullDist;
    }

    public Boolean getPartialDist() {
        return isPartialDist;
    }

    public void setPartialDist(Boolean partialDist) {
        isPartialDist = partialDist;
    }

    public Integer getAmountAfterReturn() {
        return amountAfterReturn;
    }

    public void setAmountAfterReturn(Integer amountAfterReturn) {
        this.amountAfterReturn = amountAfterReturn;
    }
}
