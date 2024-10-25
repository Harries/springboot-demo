package com.et.exception;

public enum ErrorCode {


    paramError(1, "Wrong parameters!"),
    dbError(2, "Database operation error！"),
    userNoLogin(3, "need user login！"),
    serverError(4, "server error"),
    //
    ;

    private int code;

    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
