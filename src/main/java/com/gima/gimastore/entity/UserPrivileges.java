package com.gima.gimastore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "USERS_PRIVILEGES")
public class UserPrivileges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "HAVE_USER", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveUser;
    @Column(name = "ADD_USER", columnDefinition = "BIT DEFAULT 0")
    private Boolean addUser;
    @Column(name = "EDIT_USER", columnDefinition = "BIT DEFAULT 0")
    private Boolean editUser;
    @Column(name = "BLOCK_USER", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockUser;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_PART", columnDefinition = "BIT DEFAULT 0")
    private Boolean havePart;
    @Column(name = "ADD_PART", columnDefinition = "BIT DEFAULT 0")
    private Boolean addPart;
    @Column(name = "EDIT_PART", columnDefinition = "BIT DEFAULT 0")
    private Boolean editPart;
    @Column(name = "BLOCK_PART", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockPart;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveProduct;
    @Column(name = "ADD_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    private Boolean addProduct;
    @Column(name = "EDIT_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    private Boolean editProduct;
    @Column(name = "BLOCK_PRODUCT", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockProduct;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_STORE", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveStore;
    @Column(name = "ADD_STORE", columnDefinition = "BIT DEFAULT 0")
    private Boolean addStore;
    @Column(name = "EDIT_STORE", columnDefinition = "BIT DEFAULT 0")
    private Boolean editStore;
    @Column(name = "BLOCK_STORE", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockStore;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_SUPERVISOR", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveSupervisor;
    @Column(name = "ADD_SUPERVISOR", columnDefinition = "BIT DEFAULT 0")
    private Boolean addSupervisor;
    @Column(name = "EDIT_SUPERVISOR", columnDefinition = "BIT DEFAULT 0")
    private Boolean editSupervisor;
    @Column(name = "BLOCK_SUPERVISOR", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockSupervisor;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_DEPARTMENT", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveDepartment;
    @Column(name = "ADD_DEPARTMENT", columnDefinition = "BIT DEFAULT 0")
    private Boolean addDepartment;
    @Column(name = "EDIT_DEPARTMENT", columnDefinition = "BIT DEFAULT 0")
    private Boolean editDepartment;
    @Column(name = "BLOCK_DEPARTMENT", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockDepartment;
    //////////////////////////////////////////////////////////////////////////////
    @Column(name = "HAVE_SUPPLIER", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveSupplier;
    @Column(name = "ADD_SUPPLIER", columnDefinition = "BIT DEFAULT 0")
    private Boolean addSupplier;
    @Column(name = "EDIT_SUPPLIER", columnDefinition = "BIT DEFAULT 0")
    private Boolean editSupplier;
    @Column(name = "BLOCK_SUPPLIER", columnDefinition = "BIT DEFAULT 0")
    private Boolean blockSupplier;
    //////////////////////////////////////////////////////////////////////////////
    //For SupplyProcess
    @Column(name = "HAVE_SUPPLIERPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveSupplierProcess;
    @Column(name = "ADD_SUPPLIERPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean addSupplierProcess;
    @Column(name = "EDIT_SUPPLIERPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean editSupplierProcess;
    @Column(name = "SEARCH_SUPPLIERPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean returnSupplierProcess;
    @Column(name = "VIEW_SUPPLIERPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean viewSupplierProcess;
    @Column(name = "HAVE_SUPPLIERPROCESS_DISTRIBUTION", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveSupplierProcessDist;

    //////////////////////////////////////////////////////////////////////////////
    //For StorePartsDistribution
    @Column(name = "HAVE_STOREPARTS_DISTRIBUTION", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveStorePartsDist;
    /////////////////////////////////////////////////////////////////////////////
    //For Production
    @Column(name = "HAVE_PRODUCTIONPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveProductionProcess;
    @Column(name = "ADD_PRODUCTIONPROCESS", columnDefinition = "BIT DEFAULT 0")
    private Boolean addProductionProcess;
    /////////////////////////////////////////////////////////////////////////////
    //For Stores
    @Column(name = "HAVE_STOREDETAILS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveStoreDetails;
    @Column(name = "SETTLEMENT_STORE", columnDefinition = "BIT DEFAULT 0")
    private Boolean settlementStore;
    /////////////////////////////////////////////////////////////////////////////
    //For ProductOuts
    @Column(name = "HAVE_PRODUCTOUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveProductOut;
    @Column(name = "EDIT_PRODUCTOUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean editProductOut;
    @Column(name = "ADD_PRODUCTOUT", columnDefinition = "BIT DEFAULT 0")
    private Boolean addProductOut;
    /////////////////////////////////////////////////////////////////////////////
    //For Returns
    @Column(name = "HAVE_RETURNS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveReturns;
    /////////////////////////////////////////////////////////////////////////////
    //For Reports
    @Column(name = "HAVE_REPORTS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveReports;

    @Column(name = "HAVE_DISTSTOREPARTS", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveDistStoreParts;

    @Column(name = "HAVE_ENTITIES", columnDefinition = "BIT DEFAULT 0")
    private Boolean haveEntities;
}
