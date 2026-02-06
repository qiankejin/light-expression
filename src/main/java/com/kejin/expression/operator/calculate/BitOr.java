package com.kejin.expression.operator.calculate;

import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_9;

public class BitOr extends CalculateOperator {

    @Override
    public int priority() {
        return LEVEL_9;
    }

    @Override
    public String symbol() {
        return "|";
    }

    @Override
    protected LongValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toLong() | right.toLong());
    }
}
