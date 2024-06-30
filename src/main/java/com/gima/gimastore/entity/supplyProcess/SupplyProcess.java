package com.gima.gimastore.entity.supplyProcess;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gima.gimastore.entity.Supplier;
import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "SUPPLY_PROCESS")
public class SupplyProcess implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID")
    @Nationalized
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "NOTES")
    @Nationalized
    private String notes;

    @Column(name = "BILL_ID")
    @NotNull
    private String billId;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked;

    @Lob
    @Column(name = "PICTURE", length = 1000)
    private byte[] picture;

    @Column(name = "IS_FULL_DIST", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isFullDist;
}
