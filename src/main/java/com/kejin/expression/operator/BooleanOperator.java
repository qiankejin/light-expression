package com.kejin.expression.operator;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

/**
 * 布尔计算符
 */
public abstract class BooleanOperator extends DoubleOperators {

    @Override
    public final ValueType returnType(Var left, Var right) {
        return ValueType.BOOLEAN;
    }

    @Override
    protected abstract BooleanValue operatorCalculate(Value<?> left, Value<?> right);
}
