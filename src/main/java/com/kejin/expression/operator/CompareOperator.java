package com.kejin.expression.operator;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_5;

/**
 * 比较计算符
 */
public abstract class CompareOperator extends DoubleOperators {

    @Override
    public int priority() {
        return LEVEL_5;
    }

    @Override
    public final ValueType returnType(Var left, Var right) {
        return ValueType.BOOLEAN;
    }

    public boolean supportText() {
        return false;
    }

    public boolean supportBoolean() {
        return false;
    }

    @Override
    protected abstract BooleanValue operatorCalculate(Value<?> left, Value<?> right);

    @Override
    public void check(Var left, Var right) throws ExpressionException {
        if (!supportBoolean() && (left.valueType() == ValueType.BOOLEAN || right.valueType() == ValueType.BOOLEAN)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "比较表达式左右值类型非法 " + left + this + right);
        }
        if (!supportText() && (left.valueType() == ValueType.TEXT || right.valueType() == ValueType.TEXT)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "比较表达式左右值类型非法 " + left + this + right);
        }
    }
}
