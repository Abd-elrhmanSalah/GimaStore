package com.gima.gimastore.entity.supplyProcessPartDist;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import com.gima.gimastore.entity.supplyProcess.SupplyProcessPart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "SUPPLY_PROCESS_PARTS_DIST")
public class SupplyProcessPartDist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SUPPLYPROCESSPART_ID")
    private SupplyProcessPart supplyProcessPart;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "IS_CONFIRMED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isConfirmed=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplyProcessPart getSupplyProcessPart() {
        return supplyProcessPart;
    }

    public void setSupplyProcessPart(SupplyProcessPart supplyProcessPart) {
        this.supplyProcessPart = supplyProcessPart;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
