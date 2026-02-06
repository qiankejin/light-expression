package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;

import java.math.BigDecimal;

public class DoubleValue extends Value<BigDecimal> {


    public DoubleValue(BigDecimal decimal) {
        super(decimal);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double toDouble() {
        return this.value.doubleValue();
    }

    @Override
    public long toLong() {
        return this.value.longValue();
    }

    @Override
    public int toInt() {
        return this.value.intValue();
    }

    @Override
    protected String show() {
        return this.value.toPlainString();
    }

    @Override
    public boolean eq(Value<?> value) {
        if (value instanceof DoubleValue) {
            return value.toMath().eq(this.toMath());
        } else if (value instanceof LongValue) {
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
        return Decimal.of(this.value);
    }

    @Override
    public int compareTo(Value o) {
        if (o.type() == ValueType.NUMBER) {
            return this.toMath().compareTo(o.toMath());
        }
        return this.show().compareTo(o.show());
    }
}
