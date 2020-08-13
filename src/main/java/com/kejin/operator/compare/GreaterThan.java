package com.kejin.operator.compare;


import com.kejin.operator.CompareOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

public class GreaterThan extends CompareOperator {

    @Override
    public String symbol() {
        return ">";
    }

    @Override
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (l, r) -> Value.of(l.toDouble() > r.toDouble());
    }
}
