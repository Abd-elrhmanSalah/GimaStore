package com.gima.gimastore.model.productionProcess;

import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductOutRequest implements Serializable {

    private Long id;
    private String destinationName;
    private String driverName;
    private String note;
    private String requestId;
    private User createdBy;
    private User responsibleBy;
    private Date creationDate = new Date();
    private List<ProductAmount> productAmounts;

}
