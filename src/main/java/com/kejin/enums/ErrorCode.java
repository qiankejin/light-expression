package com.kejin.enums;

public enum ErrorCode {
    ARG_MISS("ARG_MISS", "参数缺失"),
    FORMAT_ERROR("FORMAT_ERROR", "格式错误");

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
