package com.kejin.value;

import com.kejin.enums.ValueType;

public class DoubleValue extends Value {
    private final Decimal decimal;


    public DoubleValue(Decimal decimal) {
        this.decimal = decimal;
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double toDouble() {
        return decimal.toDouble();
    }

    @Override
    public int toInt() {
        return decimal.round();
    }

    @Override
    protected String show() {
        return decimal.toString();
    }

    @Override
    public boolean eq(Value value) {
        if (value instanceof DoubleValue || value instanceof IntegerValue) {
            return value.toDouble() == this.toDouble();
        }
        return false;
    }

    @Override
    public ValueType type() {
        return ValueType.NUMBER;
    }

    @Override
    public Decimal toMath() {
        return decimal.copy();
    }
}
