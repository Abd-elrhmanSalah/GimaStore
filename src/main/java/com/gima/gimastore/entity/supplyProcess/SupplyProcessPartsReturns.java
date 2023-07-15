package com.gima.gimastore.entity.supplyProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.User;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SUPPLY_PROCESS_PARTS_RETURNS")
public class SupplyProcessPartsReturns implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SUPPLY_PROCESS_ID")
    private SupplyProcess supplyProcess;
    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;

    @Column(name = "RETURN_AMOUNT")
    private Integer amountReturn;

    @Column(name = "INCOMING_AMOUNT")
    private Integer amountIncoming;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplyProcess getSupplyProcess() {
        return supplyProcess;
    }

    public void setSupplyProcess(SupplyProcess supplyProcess) {
        this.supplyProcess = supplyProcess;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getAmountReturn() {
        return amountReturn;
    }

    public void setAmountReturn(Integer amountReturn) {
        this.amountReturn = amountReturn;
    }

    public Integer getAmountIncoming() {
        return amountIncoming;
    }

    public void setAmountIncoming(Integer amountIncoming) {
        this.amountIncoming = amountIncoming;
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
