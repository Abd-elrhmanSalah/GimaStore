package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "PRODUCT_OUT")
public class ProductOut implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "DESTINATION_NAME")
    @Nationalized
    @NotNull
    private String destinationName;
    @Column(name = "DERIVER_NAME")
    @Nationalized
    @NotNull
    private String driverName;
    @Column(name = "NOTE")
    @Nationalized
    @NotNull
    private String note;
    @Column(name = "REQUEST_ID")
    @Nationalized
    @Nullable
    private String requestId;
    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "RESPONSIBLE_BY")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private User responsibleBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

}
