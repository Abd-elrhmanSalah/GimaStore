package com.gima.gimastore.constant;


public enum ResponseCodes {
    SUCCESS("111", "SUCCESS", " بنجاح"),
    SUBMIT_REQUEST("000", "Submit requests Error", "can't submit the requests "),
    NO_USER_ID("001", "UserId Error", "هذا المستخدم غير موجود"),
    REPEATED_USERNAME("002", "UserName Error", "اسم المستخدم موجود بالفعل"),
    LOGIN_FAILED("003", "Login Error", "اسم المستخدم او كلمة السر غير صحيحة"),
    PASSWORD_INCORRECT("004", "Old Password Error", "كلمة السر غير صحيحة"),

    NO_STORE_ID("005", "StoreId Error", "لايوجد مخزن بهذا الID"),
    NO_ROLE_ID("006", "RoleId Error", "لا يوجد "),
    NO_PART_ID("007", "PartId Error", "لا يوجد  "),
    EXIST_USER_WITH_STORE("008", "User Exist", "هذا المستخدم يشرف على مخزن بالفعل "),
    REPEATED_PARTNAME("009", "PartName Error", "اسم القطعة موجودة بالفعل"),
    NO_SUPPLIER_ID("010", "SupplierId Error", "لايوجد مورد بهذا الID"),
    REPEATED_SUPPLIERNAME("011", "SupplierName Error", "اسم المورد موجود بالفعل"),

    USER_EXIST_IN_STORE("012", "User and Store Erro", "لا يمكن حذف هذا المستخدم لأنه مرتبط بمخزن"),
    REPEATED_STORENAME("013", "StoreName Error", "اسم المخزن موجود بالفعل"),
    LOGIN_USER_LOCKED("014", "Locked Account Error", "هذا الحساب مغلق"),
    USER_ROLE_SUPERVISOR("015", "Supervisor Error", "هذا المستخدم ليس أمين مخزن"),

    NO_SUPPLYPROCESS_ID("016", "SupplyProcessId Error", "لا يوجد توريد بهذا الID"),
    NO_STATUS_ID("017", "StatusId Error", "لا يوجد statusID "),
    RECONCILIATION_NOT_MATCHE("017", "Reconciliation NotMatched Error", "Reconciliation NotMatched can't accepted"),
    INTERNAL_SERVER_ERROR("018", "Internal Server Error", "Internal Server Error"),
    STATUSECODE("019", "Status  Error", "Status Code unknown"),
    SOURCECODE("111", "SourceCode Error", "Source Code unknown"),
    CHANGESATUS("222", "Change Status Error", "can't change status for requestID: ");

//    public static final String SUCCESS_TRANSACTION_MESSAGE = "The transaction has been ended successfully ";

    private final String code;
    private final String key;
    private final String message;


    private ResponseCodes(String code, String key, String message) {
        this.code = code;
        this.key = key;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}
