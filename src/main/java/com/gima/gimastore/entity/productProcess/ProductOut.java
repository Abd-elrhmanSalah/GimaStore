package com.gima.gimastore.entity.productProcess;

import com.gima.gimastore.entity.User;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRODUCT_OUT")
public class ProductOut implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private Product product;

    @Column(name = "AMOUNT")
    @NotNull
    private Integer amount;
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
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @LazyCollection(value = LazyCollectionOption.TRUE)
    private User createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
