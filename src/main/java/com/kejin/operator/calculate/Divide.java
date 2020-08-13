package com.kejin.operator.calculate;


import com.kejin.operator.CalculateOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_2;

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
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (l, r) -> Value.of(l.toMath().divide(r.toMath()));
    }
}
