package com.kejin.operator;

import com.kejin.enums.ValueType;
import com.kejin.var.Var;

/**
 * 布尔计算符
 */
public abstract class BooleanOperator extends DoubleOperators {

    @Override
    public final ValueType returnType(Var left, Var right) {
        return ValueType.BOOLEAN;
    }

}
