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
    QUANTITY_PART_WITH_STORE("018", "Part and Store Quantity Error", "لايمكن سحب هذه الكمية من هذا الجزء"),
    NO_PART_WITH_STORE("019", "Part and Store Not Found", "لايوجد هذا الجزء فى هذا المخزن"),
    REPEATED_SUPPLIER_AND_BILLID("020", "Supplier and BillId Error", "هذا المورد لديه هذه الفاتورة بالفعل"),
    REPEATED_PRODUCT_NAME("021", "ProductName Error", "اسم المنتج موجودة بالفعل"),
    NO_PRODUCT_ID("022", "ProductId Error", "لا يوجد  "),
    LOCK_UNLOCK_ERROR("023", "lOCK AND ULOCK ERROR ", "يستخدم فقط مع المنتجات غير مغلقة"),
    REPEATED_DEPARTMENT_NAME("024", "DeptName Error", "اسم القسم موجود بالفعل"),
    NO_DEPARTMENT_ID("025", "DeptId Error", "لا يوجد  "),
    REPEATED_SUPERVISOR_NAME("026", "SupervisorName Error", "اسم المشرف موجود بالفعل"),
    NO_SUPERVISOR_ID("027", "SupervisorId Error", "لا يوجد  "),
    REPEATED_REQUESTEDID("028", "RequestId Error", "هذا الاذن موجود بالفعل"),
    NO_STORE_WITH_PART("029", "StorePart Error", "هذا المنتج غير مكتمل الأجزاء فى هذا المخزن"),
    NO_AMOUNT_IN_STORE_TO_PRODUCT("030", "StorePartAmount Error", "لا توجد كميات كافية من الأجزاء لهذا المنتج لهذه العملية"),
    INTERNAL_SERVER_ERROR("024", "Internal Server Error", "Internal Server Error");

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
