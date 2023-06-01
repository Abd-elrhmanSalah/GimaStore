package com.gima.gimastore.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "SUPPLY_PROCESS_PARTS")
public class SupplyProcessParts implements Serializable {
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
}
