package com.gima.gimastore.entity.supplyProcess;

import com.gima.gimastore.entity.Part;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
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

}
