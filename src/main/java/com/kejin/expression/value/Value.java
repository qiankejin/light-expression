package com.kejin.expression.value;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;

import java.math.BigDecimal;
import java.util.Date;

import static com.kejin.expression.value.BooleanValue.FALSE;
import static com.kejin.expression.value.BooleanValue.TRUE;

public abstract class Value<T> implements Comparable<Value<?>> {

    protected final T value;

    public Value(T value) {
        this.value = value;
    }

    public abstract ValueType type();


    public static DoubleValue of(BigDecimal bigDecimal) {
        return new DoubleValue(bigDecimal);
    }

    public static DoubleValue of(Double number) {
        if (number == null) {
            return new DoubleValue(BigDecimal.ZERO);
        }
        return new DoubleValue(new BigDecimal(number));
    }

    public static DoubleValue of(Decimal decimal) {
        return new DoubleValue(decimal.toDecimal());
    }

    public static LongValue of(Integer number) {
        return new LongValue(Long.valueOf(number));
    }

    public static DateValue of(Date value) {
        return new DateValue(value);
    }

    public static LongValue of(Long value) {
        return new LongValue(value);
    }

    public static TextValue of(String value) {
        return new TextValue(value);
    }

    public static BooleanValue of(Boolean bl) {
        return bl ? TRUE : FALSE;
    }

    public static Value<?> of(Object value) {
        if (value instanceof Value) {
            return (Value<?>) value;
        }
        if (value instanceof Boolean) {
            return of((Boolean) value);
        }
        if (value instanceof String) {
            return of((String) value);
        }
        if (value instanceof Double) {
            return of(BigDecimal.valueOf((Double) value));
        }
        if (value instanceof BigDecimal) {
            return of((BigDecimal) value);
        }
        if (value instanceof Integer) {
            return of((Integer) value);
        }
        if (value instanceof Long) {
            return of((Long) value);
        }
        if (value instanceof Date) {
            return of((Date) value);
        }
        throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "数据类型不支持" + value.getClass());
    }


    public boolean toBoolean() {
        throw new RuntimeException(show() + "不能转换成布尔值");
    }

    public double toDouble() {
        throw new RuntimeException(show() + "不能转换成数字");
    }

    public long toLong() {
        throw new RuntimeException(show() + "不能转换成数字");
    }

    public int toInt() {
        throw new RuntimeException(show() + "不能转换成数字");
    }

    public Decimal toMath() {
        throw new RuntimeException(show() + "不能转换成数字");
    }

    public Date toDate() {
        throw new RuntimeException(show() + "不能转换成日期");
    }

    public Object toValue() {
        return this.value;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isText() {
        return false;
    }

    public boolean isDate() {
        return false;
    }

    public boolean isLong() {
        return false;
    }

    public boolean isInteger() {
        return false;
    }

    protected abstract String show();

    @Override
    public String toString() {
        return show();
    }

    public abstract boolean eq(Value<?> value);
}
