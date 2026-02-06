package com.kejin.expression.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ;
    private final int code;
    private final String errorCode;
    private final String errorMessage;

}
