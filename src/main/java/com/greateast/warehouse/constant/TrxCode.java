package com.greateast.warehouse.constant;

public enum TrxCode {

    TRX_OK("0000", 200, "OK"),
    TRX_CREATED("0201", 201, "Data is successfully saved"),
    TRX_NOT_FOUND("0204", 204,"Data cannot be found"),
    TRX_BAD_REQUEST("0400", 400,"Bad request data"),
    TRX_UNPROCESSABLE_ENTITY("0422", 422,"Cannot proceed data"),
    TRX_UNKNOWN("0500", 500,"Unknown error, contact your admin!"),
    TRX_SERVICE_NOT_AVAILABLE("0503", 503,"Error Service is not available!");

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

