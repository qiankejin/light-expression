package com.kejin.expression.operator.calculate;


import com.kejin.expression.operator.BooleanOperator;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.function.BiFunction;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_10;

public class And extends BooleanOperator {

    @Override
    public int priority() {
        return LEVEL_10;
    }

    @Override
    public String symbol() {
        return "&&";
    }

    @Override
    protected BooleanValue operatorCalculate(Value<?> left, Value<?> right) {
        return Value.of(left.toBoolean() && right.toBoolean());
    }

}
