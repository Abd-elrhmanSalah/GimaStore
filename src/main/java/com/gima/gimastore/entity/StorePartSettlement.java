package com.gima.gimastore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "STORE_PART_SETTLEMENT")
public class StorePartSettlement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    private Part part;
    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
    @Column(name = "PREVIOUS_AMOUNT")
    private Integer amountPrevious;
    @Column(name = "UPDATED_AMOUNT")
    private Integer amountUpdate;
    @Column(name = "DIFF_AMOUNT")
    private Integer amountDiff;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;
    @Column(name = "CREATION_DATE")
    private Date creationDate;
}
