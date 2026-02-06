package com.kejin.expression.operator.calculate;


import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.DoubleValue;
import com.kejin.expression.value.Value;

import java.util.function.BiFunction;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_2;

public class Divide extends CalculateOperator {

    @Override
    public int priority() {
        return LEVEL_2;
    }

    @Override
    public String symbol() {
        return "/";
    }

    @Override
    protected DoubleValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toMath().divide(right.toMath()));
    }
}
