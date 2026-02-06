package com.kejin.expression.errors;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.Errors;

import java.text.MessageFormat;

/**
 * 表达式编译异常
 *
 */
public class ExpressionException extends RuntimeException implements Errors {
    private final int code;
    private final String msg;
    private final String errorCode;
    private final String[] msgParam;

    private ExpressionException(int code, String errorCode, String msg) {
        super("[" + code + "]" + msg);
        this.code = code;
        this.msg = msg;
        this.errorCode = errorCode;
        this.msgParam = null;
    }

    private ExpressionException(int code, String errorCode, String msg, Exception e, Object... args) {
        super("[" + code + "]" + msg, e);
        this.code = code;
        this.msg = msg;
        this.errorCode = errorCode;
        String[] param = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            param[i] = String.valueOf(args[i]);
        }
        this.msgParam = param;
    }

    private ExpressionException(int code, String errorCode, String msg, Object... args) {
        super("[" + code + "]" + msg);
        this.code = code;
        this.msg = msg;
        this.errorCode = errorCode;
        String[] param = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            param[i] = String.valueOf(args[i]);
        }
        this.msgParam = param;
    }

    private ExpressionException(int code, String errorCode, String msg, Exception e) {
        super("[" + code + "]" + msg, e);
        this.code = code;
        this.msg = msg;
        this.errorCode = errorCode;
        this.msgParam = null;
    }

    public ExpressionException(Errors errors) {
        this(errors.getCode(), errors.getErrorCode(), errors.getMsg());
    }

    public ExpressionException(Errors errors, Exception e) {
        this(errors.getCode(), errors.getErrorCode(), errors.getMsg(), e);
    }

    public ExpressionException(Errors errors, Exception e, Object... args) {
        this(errors.getCode(), errors.getErrorCode(), MessageFormat.format(errors.getMsg(), args), e, args);
    }

    public ExpressionException(Errors errors, Object... args) {
        this(errors.getCode(), errors.getErrorCode(), MessageFormat.format(errors.getMsg(), args), args);
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }


    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
