package com.kejin.expression.operator.compare;


import com.kejin.expression.operator.CompareOperator;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_6;

public class Equal extends CompareOperator {
    @Override
    public boolean supportText() {
        return true;
    }

    @Override
    public boolean supportBoolean() {
        return true;
    }

    @Override
    public String symbol() {
        return "==";
    }

    @Override
    public int priority() {
        return LEVEL_6;
    }

    @Override
    protected BooleanValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.eq(right));
    }
}
