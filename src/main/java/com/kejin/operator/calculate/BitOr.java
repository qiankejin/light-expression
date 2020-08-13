package com.kejin.operator.calculate;

import com.kejin.operator.CalculateOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_9;

public class BitOr extends CalculateOperator {

    @Override
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (left, right) -> Value.of(left.toInt() | right.toInt());
    }

    @Override
    public int priority() {
        return LEVEL_9;
    }

    @Override
    public String symbol() {
        return "|";
    }
}
