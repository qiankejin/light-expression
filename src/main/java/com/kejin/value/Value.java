package com.kejin.value;

import com.kejin.enums.CompileException;
import com.kejin.enums.ErrorCode;
import com.kejin.enums.ValueType;
import com.kejin.var.NumberConst;

import static com.kejin.value.BooleanValue.FALSE;
import static com.kejin.value.BooleanValue.TRUE;

public abstract class Value {


    public boolean isSuccess() {
        return true;
    }

    public abstract ValueType type();


    protected Value() {
    }

    public static Value error(ErrorCode errorCode, String errorMessage) {
        return new ErrorValue(errorCode, errorMessage);
    }

    public static Value of(Integer number) {
        return new IntegerValue(number);
    }

    public static Value of(Double number) {
        return new DoubleValue(Decimal.of(number));
    }

    public static Value of(Decimal decimal) {
        return new DoubleValue(decimal);
    }

    public static Value of(Boolean bl) {
        return bl ? TRUE : FALSE;
    }

    public static Value ofText(String text) {
        return new TextValue(text);
    }

    public static void check(String value, ValueType valueType) throws CompileException {
        switch (valueType) {
            case BOOLEAN:
            case TEXT:
                if (of(value).type() != valueType) {
                    throw new CompileException(value + "类型不满足期望" + valueType);
                }
            case NUMBER:
                NumberConst.of(value);
        }
    }

    public static Value of(String value, ValueType valueType) throws CompileException {
        switch (valueType) {
            case BOOLEAN:
            case TEXT:
                Value v = of(value);
                if (v.type() != valueType) {
                    throw new CompileException(value + "类型不满足期望" + valueType);
                }
                return v;
            case NUMBER:
                try {
                    if (value.contains(".")) {
                        return of(Double.parseDouble(value));
                    } else {
                        return of(Integer.parseInt(value));
                    }
                } catch (NumberFormatException e) {
                    throw new CompileException(value + "类型不满足期望" + valueType);
                }
            default:
                throw new CompileException("值类型无法识别");
        }
    }


    public static Value of(String text) {
        if (text.equalsIgnoreCase("true")) {
            return TRUE;
        }
        if (text.equalsIgnoreCase("false")) {
            return FALSE;
        }
        return new TextValue(text);
    }


    public boolean toBoolean() {
        throw new RuntimeException("不能进行转换");
    }

    public int toInt() {
        throw new RuntimeException("不能进行转换");
    }

    public double toDouble() {
        throw new RuntimeException("不能进行转换");
    }
    public Decimal toMath() {
        throw new RuntimeException("不能进行转换");
    }

    public boolean isDouble() {
        return false;
    }

    protected abstract String show();

    @Override
    public String toString() {
        return show();
    }

    public abstract boolean eq(Value value);
}
