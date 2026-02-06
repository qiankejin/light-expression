package com.kejin.expression.operator.compare;


import com.kejin.expression.operator.CompareOperator;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;

import java.util.function.BiFunction;

public class GreaterThanOE extends CompareOperator {

    @Override
    public String symbol() {
        return ">=";
    }

    @Override
    protected BooleanValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.compareTo(right) >= 0);
    }
}
