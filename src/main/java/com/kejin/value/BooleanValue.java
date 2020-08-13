package com.kejin.value;

import com.kejin.enums.ValueType;

public class BooleanValue extends Value {
    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);
    private final boolean bl;

    private BooleanValue(boolean bl) {
        this.bl = bl;
    }

    @Override
    public boolean toBoolean() {
        return bl;
    }

    @Override
    public ValueType type() {
        return ValueType.BOOLEAN;
    }

    @Override
    protected String show() {
        return bl ? "true" : "false";
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof BooleanValue) {
            return bl == ((BooleanValue) value).bl;
        }
        return false;
    }

}
