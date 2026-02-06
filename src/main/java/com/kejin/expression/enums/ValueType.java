package com.kejin.expression.enums;

public enum ValueType implements IntegerEnum {

    BOOLEAN(0, "布尔型"),
    NUMBER(1, "数值"),
    TEXT(2, "文本型"),
    DATE(3, "日期"),
    ;
    private final int code;
    private final String desc;

    ValueType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public boolean accept(ValueType targetType) {
        return this == targetType;
    }
}
