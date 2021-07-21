package com.kejin.enums;

public class CompileException extends RuntimeException {

    private final String error;

    public CompileException(String error) {
        super(error);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
