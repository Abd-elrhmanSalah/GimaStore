package com.gima.gimastore.model;

import com.gima.gimastore.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
@Data
@RequiredArgsConstructor
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
    private Boolean haveDistStoreParts = false;

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

    private Boolean haveEntities = false;

}
