package com.kejin.value;

import com.kejin.enums.ErrorCode;
import com.kejin.enums.ValueType;

public class ErrorValue extends Value {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public ErrorValue(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    @Override
    public ValueType type() {
        return ValueType.NONE;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    protected String show() {
        return errorCode + ":" + errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean eq(Value value) {
        return false;
    }
}
