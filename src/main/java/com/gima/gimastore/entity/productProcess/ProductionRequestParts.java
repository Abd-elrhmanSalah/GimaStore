package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "PRODUCT_REQUEST_PARTS")
public class ProductionRequestParts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_REQUEST_ID")
    @Nullable
    private ProductionRequest productionRequest;

    @ManyToOne
    @JoinColumn(name = "PART_ID")
    @Nullable
    private Part part;

    @Column(name = "REQUESTED_AMOUNT")
    @NotNull
    private Integer requestedAmount;

}
