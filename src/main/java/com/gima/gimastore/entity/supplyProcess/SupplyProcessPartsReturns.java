package com.gima.gimastore.entity.supplyProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
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
}
