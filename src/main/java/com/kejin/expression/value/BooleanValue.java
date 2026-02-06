package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;

public class BooleanValue extends Value<Boolean> {
    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);

    private BooleanValue(Boolean value) {
        super(value);
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public ValueType type() {
        return ValueType.BOOLEAN;
    }

    @Override
    protected String show() {
        return value ? "true" : "false";
    }

    @Override
    public boolean eq(Value<?> value) {
        if (value instanceof BooleanValue) {
            return this.value == value.value;
        }
        return false;
    }

    @Override
    public int compareTo(Value o) {
        return this.show().compareTo(o.show());
    }
}
