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

    CREDITBUDGET_CATEGOR("006", "CreditBudget Category Error", "CreditBudget Category code not exist"),
    CREDITBUDGET_TYPE("007", "CreditBudget Type Error", "CreditBudget Type code not exist"),

    DEPIT_CATEGORY_AND_TYPE("008", "DebitBudget CategoryAndType Error", "Debit Budget Category code and  type not exist"),
    CREDIT_CATEGORY_AND_TYPE("009", "CreditBudget CategoryAndType Error", "Credit Budget Category code and  type not exist"),
    DEBIT_ACCOUNT_NUMBER("010", "Debit Account Error", "Debit account number not exist"),
    CREDIT_ACCOUNT_NUMBER("011", "Debit Account Error", "Credit account number not exist"),
    NO_DEBIT_ACCOUNT("012", "Debit Account Error", "Can't find Debit account"),
    NO_CREDIT_ACCOUNT("013", "Credit Accounts Error", "Can't find Credit account"),
    NO_DEBIT_NO_CREDIT("014", "Accounts Error", "Can't find accounts numbers"),
    FETCH_AVAILABLE_BALANCE("015", "Fetch AvailableBalance Error", "Can't fetch available balance with this debit account and this budgetCategory"),
    BALANCE_NOT_AVAILABLE("016", "BalanceNotAvailable Error", "Balance Not Available availableBalance is : "),
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
