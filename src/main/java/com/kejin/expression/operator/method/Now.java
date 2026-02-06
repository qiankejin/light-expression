package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.NonArgMethod;
import com.kejin.expression.value.Value;

import java.util.Date;

public class Now extends NonArgMethod {
    @Override
    protected Value<?> methodCalculate() {
        return Value.of(new Date());
    }

    @Override
    public ValueType methodReturnType() {
        return ValueType.DATE;
    }

    @Override
    public String symbol() {
        return "NOW";
    }
}
