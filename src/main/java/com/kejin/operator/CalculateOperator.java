package com.kejin.operator;


import com.kejin.enums.ValueType;
import com.kejin.var.Var;

/**
 * 算术计算符
 * */
public abstract class CalculateOperator extends DoubleOperators {
    @Override
    public ValueType returnType(Var left, Var right) {
        return ValueType.NUMBER;
    }

}
