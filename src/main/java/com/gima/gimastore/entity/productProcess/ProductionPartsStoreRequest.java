package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "PRODUCTION_PARTS_STORE_REQUEST")
public class ProductionPartsStoreRequest implements Serializable {
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
    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;
    @Column(name = "REQUESTED_AMOUNT")
    @NotNull
    private Integer requestedAmount;
    @Column(name = "OUTED_AMOUNT")
    @NotNull
    private Integer outedAmount;
    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdateDate;
    @Column(name = "IS_FULL_OUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean isFullOut = false;

    public ProductionPartsStoreRequest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductionPartsStoreRequest psr = (ProductionPartsStoreRequest) o;

        return getProductionRequest() == psr.getProductionRequest();
    }

}
