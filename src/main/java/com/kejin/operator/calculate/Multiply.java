package com.kejin.operator.calculate;


import com.kejin.operator.CalculateOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_2;

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
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (l, r) -> {
            if (l.isDouble() || r.isDouble()) {
                return Value.of(l.toMath().multiply(r.toMath()));
            }
            return Value.of(l.toInt() * r.toInt());
        };
    }
}
