package com.kejin.operator.compare;


import com.kejin.operator.CompareOperator;
import com.kejin.value.Value;

import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_6;

public class Equal extends CompareOperator {
    @Override
    public boolean supportText() {
        return true;
    }

    @Override
    public boolean supportBoolean() {
        return true;
    }

    public static final Equal instance = new Equal();


    @Override
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return (l, r) -> Value.of(l.eq(r));
    }

    @Override
    public String symbol() {
        return "==";
    }

    @Override
    public int priority() {
        return LEVEL_6;
    }
}
