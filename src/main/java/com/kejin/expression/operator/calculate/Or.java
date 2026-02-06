package com.kejin.expression.operator.calculate;


import com.kejin.expression.operator.BooleanOperator;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;

import java.util.function.BiFunction;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_11;

public class Or extends BooleanOperator {

    @Override
    public int priority() {
        return LEVEL_11;
    }

    @Override
    public String symbol() {
        return "||";
    }

    @Override
    protected BooleanValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toBoolean() || right.toBoolean());
    }
}
