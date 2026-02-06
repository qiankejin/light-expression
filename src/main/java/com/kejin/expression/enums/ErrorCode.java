package com.kejin.expression.enums;


public enum ErrorCode implements Errors {
    ARG_MISS(80000001, "参数缺失[{0}]"),
    FORMAT_ERROR(80000002, "格式错误:[{0}]"),
    GRAMMAR_ERROR(80000003, "语法错误:[{0}]"),
    CALCULATE_ERROR(80000004, "计算错误:[{0}]"),

    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getErrorCode() {
        return name();
    }

    @Override
    public String getMsg() {
        return message;
    }

}
