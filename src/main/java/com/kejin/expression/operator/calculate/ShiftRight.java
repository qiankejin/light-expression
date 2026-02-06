package com.kejin.expression.operator.calculate;

import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.Value;

import java.util.function.BiFunction;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_4;

public class ShiftRight extends CalculateOperator {

    @Override
    public int priority() {
        return LEVEL_4;
    }

    @Override
    public String symbol() {
        return ">>>";
    }

    @Override
    protected Value<?> operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toInt() >>> right.toInt());
    }
}
