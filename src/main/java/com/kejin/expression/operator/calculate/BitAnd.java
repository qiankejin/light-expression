package com.kejin.expression.operator.calculate;

import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_7;

public class BitAnd extends CalculateOperator {


    @Override
    public int priority() {
        return LEVEL_7;
    }

    @Override
    public String symbol() {
        return "&";
    }

    @Override
    protected LongValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toInt() & right.toInt());
    }
}
