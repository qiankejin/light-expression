package com.kejin.value;

import com.kejin.enums.ValueType;

public class IntegerValue extends Value {
    private final Integer number;


    public IntegerValue(Integer number) {
        this.number = number;
    }

    @Override
    public Decimal toMath() {
        return Decimal.of(number);
    }

    @Override
    protected String show() {
        return number.toString();
    }

    @Override
    public double toDouble() {
        return number;
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
    public int toInt() {
        return number;
    }
}

