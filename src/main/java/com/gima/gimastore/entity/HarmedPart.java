package com.gima.gimastore.entity;

import com.gima.gimastore.entity.supplyProcess.SupplyProcess;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "HARMED_PART")
public class HarmedPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;
    @Column(name = "HARMED_AMOUNT")
    private Integer amountHarmed;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "billId")
    private String billId;

}
