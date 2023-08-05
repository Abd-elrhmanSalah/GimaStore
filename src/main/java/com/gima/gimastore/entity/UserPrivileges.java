package com.gima.gimastore.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getHaveUser() {
        return haveUser;
    }

    public void setHaveUser(Boolean haveUser) {
        this.haveUser = haveUser;
    }

    public Boolean getAddUser() {
        return addUser;
    }

    public void setAddUser(Boolean addUser) {
        this.addUser = addUser;
    }

    public Boolean getEditUser() {
        return editUser;
    }

    public void setEditUser(Boolean editUser) {
        this.editUser = editUser;
    }

    public Boolean getBlockUser() {
        return blockUser;
    }

    public void setBlockUser(Boolean blockUser) {
        this.blockUser = blockUser;
    }

    public Boolean getHavePart() {
        return havePart;
    }

    public void setHavePart(Boolean havePart) {
        this.havePart = havePart;
    }

    public Boolean getAddPart() {
        return addPart;
    }

    public void setAddPart(Boolean addPart) {
        this.addPart = addPart;
    }

    public Boolean getEditPart() {
        return editPart;
    }

    public void setEditPart(Boolean editPart) {
        this.editPart = editPart;
    }

    public Boolean getBlockPart() {
        return blockPart;
    }

    public void setBlockPart(Boolean blockPart) {
        this.blockPart = blockPart;
    }

    public Boolean getHaveProduct() {
        return haveProduct;
    }

    public void setHaveProduct(Boolean haveProduct) {
        this.haveProduct = haveProduct;
    }

    public Boolean getAddProduct() {
        return addProduct;
    }

    public void setAddProduct(Boolean addProduct) {
        this.addProduct = addProduct;
    }

    public Boolean getEditProduct() {
        return editProduct;
    }

    public void setEditProduct(Boolean editProduct) {
        this.editProduct = editProduct;
    }

    public Boolean getBlockProduct() {
        return blockProduct;
    }

    public void setBlockProduct(Boolean blockProduct) {
        this.blockProduct = blockProduct;
    }

    public Boolean getHaveStore() {
        return haveStore;
    }

    public void setHaveStore(Boolean haveStore) {
        this.haveStore = haveStore;
    }

    public Boolean getAddStore() {
        return addStore;
    }

    public void setAddStore(Boolean addStore) {
        this.addStore = addStore;
    }

    public Boolean getEditStore() {
        return editStore;
    }

    public void setEditStore(Boolean editStore) {
        this.editStore = editStore;
    }

    public Boolean getBlockStore() {
        return blockStore;
    }

    public void setBlockStore(Boolean blockStore) {
        this.blockStore = blockStore;
    }

    public Boolean getHaveSupervisor() {
        return haveSupervisor;
    }

    public void setHaveSupervisor(Boolean haveSupervisor) {
        this.haveSupervisor = haveSupervisor;
    }

    public Boolean getAddSupervisor() {
        return addSupervisor;
    }

    public void setAddSupervisor(Boolean addSupervisor) {
        this.addSupervisor = addSupervisor;
    }

    public Boolean getEditSupervisor() {
        return editSupervisor;
    }

    public void setEditSupervisor(Boolean editSupervisor) {
        this.editSupervisor = editSupervisor;
    }

    public Boolean getBlockSupervisor() {
        return blockSupervisor;
    }

    public void setBlockSupervisor(Boolean blockSupervisor) {
        this.blockSupervisor = blockSupervisor;
    }

    public Boolean getHaveDepartment() {
        return haveDepartment;
    }

    public void setHaveDepartment(Boolean haveDepartment) {
        this.haveDepartment = haveDepartment;
    }

    public Boolean getAddDepartment() {
        return addDepartment;
    }

    public void setAddDepartment(Boolean addDepartment) {
        this.addDepartment = addDepartment;
    }

    public Boolean getEditDepartment() {
        return editDepartment;
    }

    public void setEditDepartment(Boolean editDepartment) {
        this.editDepartment = editDepartment;
    }

    public Boolean getBlockDepartment() {
        return blockDepartment;
    }

    public void setBlockDepartment(Boolean blockDepartment) {
        this.blockDepartment = blockDepartment;
    }

    public Boolean getHaveSupplier() {
        return haveSupplier;
    }

    public void setHaveSupplier(Boolean haveSupplier) {
        this.haveSupplier = haveSupplier;
    }

    public Boolean getAddSupplier() {
        return addSupplier;
    }

    public void setAddSupplier(Boolean addSupplier) {
        this.addSupplier = addSupplier;
    }

    public Boolean getEditSupplier() {
        return editSupplier;
    }

    public void setEditSupplier(Boolean editSupplier) {
        this.editSupplier = editSupplier;
    }

    public Boolean getBlockSupplier() {
        return blockSupplier;
    }

    public void setBlockSupplier(Boolean blockSupplier) {
        this.blockSupplier = blockSupplier;
    }

    public Boolean getHaveSupplierProcess() {
        return haveSupplierProcess;
    }

    public void setHaveSupplierProcess(Boolean haveSupplierProcess) {
        this.haveSupplierProcess = haveSupplierProcess;
    }

    public Boolean getAddSupplierProcess() {
        return addSupplierProcess;
    }

    public void setAddSupplierProcess(Boolean addSupplierProcess) {
        this.addSupplierProcess = addSupplierProcess;
    }

    public Boolean getEditSupplierProcess() {
        return editSupplierProcess;
    }

    public void setEditSupplierProcess(Boolean editSupplierProcess) {
        this.editSupplierProcess = editSupplierProcess;
    }

    public Boolean getViewSupplierProcess() {
        return viewSupplierProcess;
    }

    public void setViewSupplierProcess(Boolean viewSupplierProcess) {
        this.viewSupplierProcess = viewSupplierProcess;
    }

    public Boolean getHaveSupplierProcessDist() {
        return haveSupplierProcessDist;
    }

    public void setHaveSupplierProcessDist(Boolean haveSupplierProcessDist) {
        this.haveSupplierProcessDist = haveSupplierProcessDist;
    }

    public Boolean getHaveStorePartsDist() {
        return haveStorePartsDist;
    }

    public void setHaveStorePartsDist(Boolean haveStorePartsDist) {
        this.haveStorePartsDist = haveStorePartsDist;
    }

    public Boolean getReturnSupplierProcess() {
        return returnSupplierProcess;
    }

    public void setReturnSupplierProcess(Boolean returnSupplierProcess) {
        this.returnSupplierProcess = returnSupplierProcess;
    }

    public Boolean getHaveProductionProcess() {
        return haveProductionProcess;
    }

    public void setHaveProductionProcess(Boolean haveProductionProcess) {
        this.haveProductionProcess = haveProductionProcess;
    }

    public Boolean getAddProductionProcess() {
        return addProductionProcess;
    }

    public void setAddProductionProcess(Boolean addProductionProcess) {
        this.addProductionProcess = addProductionProcess;
    }

    public Boolean getHaveStoreDetails() {
        return haveStoreDetails;
    }

    public void setHaveStoreDetails(Boolean haveStoreDetails) {
        this.haveStoreDetails = haveStoreDetails;
    }

    public Boolean getSettlementStore() {
        return settlementStore;
    }

    public void setSettlementStore(Boolean settlementStore) {
        this.settlementStore = settlementStore;
    }

    public Boolean getHaveProductOut() {
        return haveProductOut;
    }

    public void setHaveProductOut(Boolean haveProductOut) {
        this.haveProductOut = haveProductOut;
    }

    public Boolean getEditProductOut() {
        return editProductOut;
    }

    public void setEditProductOut(Boolean editProductOut) {
        this.editProductOut = editProductOut;
    }

    public Boolean getAddProductOut() {
        return addProductOut;
    }

    public void setAddProductOut(Boolean addProductOut) {
        this.addProductOut = addProductOut;
    }

    public Boolean getHaveReturns() {
        return haveReturns;
    }

    public void setHaveReturns(Boolean haveReturns) {
        this.haveReturns = haveReturns;
    }

    public Boolean getHaveReports() {
        return haveReports;
    }

    public void setHaveReports(Boolean haveReports) {
        this.haveReports = haveReports;
    }

    public Boolean getHaveDistStoreParts() {
        return haveDistStoreParts;
    }

    public void setHaveDistStoreParts(Boolean haveDistStoreParts) {
        this.haveDistStoreParts = haveDistStoreParts;
    }

    public Boolean getHaveEntities() {
        return haveEntities;
    }

    public void setHaveEntities(Boolean haveEntities) {
        this.haveEntities = haveEntities;
    }
}
