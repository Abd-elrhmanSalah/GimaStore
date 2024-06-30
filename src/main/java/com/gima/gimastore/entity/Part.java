package com.gima.gimastore.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "PART")
public class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PART_NAME")
    @Nationalized
    @NotNull
    private String partName;

    @Column(name = "CURRENT_COST")
    @NotNull
    private BigDecimal currentCost;

    @Lob
    @Column(name = "PICTURE", length = 1000)
    @Nullable
    private byte[] picture;

    @Column(name = "IS_LOCKED", columnDefinition = "BIT DEFAULT 0")
    @NotNull
    private Boolean isLocked = false;

    @Column(name = "MIN_AMOUNT")
    @NotNull
    private Integer minAmount = 1;


}
