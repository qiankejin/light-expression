package com.kejin.expression.operator.calculate;


import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.Value;

import java.util.function.BiFunction;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_2;

public class Multiply extends CalculateOperator {

    @Override
    public int priority() {
        return LEVEL_2;
    }

    @Override
    public String symbol() {
        return "*";
    }

    @Override
    protected Value<?> operatorCalculate(Value<?> left, Value<?> right) {
        if (left.isDouble() || right.isDouble()) {
            return Value.of(left.toMath().multiply(right.toMath()));
        }
        return Value.of(left.toInt() * right.toInt());
    }
}
