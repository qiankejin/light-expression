package com.kejin.expression.value;


import com.kejin.expression.enums.ValueType;

public class LongValue extends Value<Long> {


    public LongValue(Long number) {
        super(number);
    }

    @Override
    public Decimal toMath() {
        return Decimal.of(value);
    }

    @Override
    protected String show() {
        return value.toString();
    }

    @Override
    public long toLong() {
        return value;
    }

    @Override
    public double toDouble() {
        return value;
    }

    @Override
    public int toInt() {
        return value.intValue();
    }

    @Override
    public boolean eq(Value<?> value) {
        if (value instanceof DoubleValue || value instanceof LongValue) {
            return value.toDouble() == this.toDouble();
        }
        return false;
    }

    @Override
    public ValueType type() {
        return ValueType.NUMBER;
    }

    @Override
    public int compareTo(Value<?> o) {
        if (o instanceof LongValue) {
            return Long.compare(this.value, ((LongValue) o).value);
        }
        return this.show().compareTo(o.show());
    }
}

