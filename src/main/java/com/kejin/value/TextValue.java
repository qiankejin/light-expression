package com.kejin.value;

import com.kejin.enums.ValueType;

public class TextValue extends Value {

    private String text;

    public TextValue(String text) {
        this.text = text;
    }


    @Override
    public ValueType type() {
        return ValueType.TEXT;
    }

    @Override
    protected String show() {
        return text;
    }

    @Override
    public boolean eq(Value value) {
        return text.equals(value.show());
    }

}
