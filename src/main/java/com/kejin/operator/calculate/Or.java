package com.kejin.operator.calculate;


import com.kejin.operator.BooleanOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_11;

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
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (l, r) -> Value.of(l.toBoolean() || r.toBoolean());
    }

}
