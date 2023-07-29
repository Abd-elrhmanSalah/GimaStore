package com.gima.gimastore.model;

import com.gima.gimastore.entity.User;

import javax.persistence.Column;
import java.io.Serializable;

public class UserPrivilegesDTO implements Serializable {
    private Long id;
    private User user;
    private Boolean haveUser = false;
    private Boolean addUser = false;
    private Boolean editUser = false;
    private Boolean blockUser = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean havePart = false;
    private Boolean addPart = false;
    private Boolean editPart = false;
    private Boolean blockPart = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean haveProduct = false;
    private Boolean addProduct = false;
    private Boolean editProduct = false;
    private Boolean blockProduct = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean haveStore = false;
    private Boolean addStore = false;
    private Boolean editStore = false;
    private Boolean blockStore = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean haveSupervisor = false;
    private Boolean addSupervisor = false;
    private Boolean editSupervisor = false;
    private Boolean blockSupervisor = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean haveDepartment = false;
    private Boolean addDepartment = false;
    private Boolean editDepartment = false;
    private Boolean blockDepartment = false;
    //////////////////////////////////////////////////////////////////////////////
    private Boolean haveSupplier = false;
    private Boolean addSupplier = false;
    private Boolean editSupplier = false;
    private Boolean blockSupplier = false;
    ////////////////////////////////////////////////////////////////////////////////////
    // For SupplyProcess
    private Boolean haveSupplierProcess = false;
    private Boolean addSupplierProcess = false;
    private Boolean editSupplierProcess = false;
    private Boolean returnSupplierProcess = false;
    private Boolean viewSupplierProcess = false;
    private Boolean haveSupplierProcessDist = false;

    //////////////////////////////////////////////////////////////////////////////
    //For StorePartsDistribution
    private Boolean haveStorePartsDist = false;
    /////////////////////////////////////////////////////////////////////////////
    //For Production

    private Boolean haveProductionProcess = false;
    private Boolean addProductionProcess = false;
    /////////////////////////////////////////////////////////////////////////////
    //For Stores
    private Boolean haveStoreDetails = false;
    private Boolean settlementStore = false;
    /////////////////////////////////////////////////////////////////////////////
    //For ProductOuts
    private Boolean haveProductOut = false;
    private Boolean editProductOut = false;
    private Boolean addProductOut = false;
    /////////////////////////////////////////////////////////////////////////////
    //For Returns
    private Boolean haveReturns = false;
    /////////////////////////////////////////////////////////////////////////////
    //For Reports
    private Boolean haveReports = false;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
