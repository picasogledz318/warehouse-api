package com.greateast.warehouse.constant;

public enum TrxCode {

    TRX_OK("0000", 200, "OK"),
    TRX_SAVED("0201", 201, "Data is successfully saved"),
    TRX_PENDING("0202", 202, "Transaction pending"),
    TRX_SUCCESS("0203", 203, "Transaction success"),

    TRX_CANCELLED("0204", 204, "Transaction cancelled"),
    TRX_DELETED("0205", 205, "Deleted successfully"),
    TRX_STOCK_AVAILABLE("0206", 206, "Stock available"),
    TRX_NOT_FOUND("0404", 404, "Data cannot be found!"),
    TRX_INSUFFICIENT_STOCK("0422", 422, "Insufficient stock/unit!"),
    TRX_INSUFFICIENT_PAYMENT("0423", 423, "Insufficient payment!"),
    TRX_BAD_REQUEST("0400", 400, "Bad request data"),
    TRX_UNKNOWN_ERROR("0500", 500, "Unknown error, contact your admin!"),
    TRX_SERVICE_NOT_AVAILABLE("0503", 503, "Error Service is not available!");

    private String code;
    private int numericCode;
    private String description;

    TrxCode(String code, int numericCode, String description) {
        this.code = code;
        this.numericCode = numericCode;
        this.description = description;
    }

    public String code() {
        return code;
    }

    public int numericCode() {
        return numericCode;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("%s-%s", code, description);
    }
}

