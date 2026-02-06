package com.kejin.expression.operator;


import com.kejin.expression.enums.ValueType;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

/**
 * 算术计算符
 */
public abstract class CalculateOperator extends DoubleOperators {
    @Override
    public ValueType returnType(Var left, Var right) {
        return ValueType.NUMBER;
    }

}
