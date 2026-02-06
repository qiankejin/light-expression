package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;

public class TextValue extends Value<String> {


    public TextValue(String text) {
        super(text);
    }

    @Override
    public ValueType type() {
        return ValueType.TEXT;
    }

    @Override
    protected String show() {
        return this.value;
    }

    @Override
    public boolean eq(Value<?> value) {
        return this.value.equals(value.show());
    }

    @Override
    public int compareTo(Value o) {
        return this.show().compareTo(o.show());
    }
}
