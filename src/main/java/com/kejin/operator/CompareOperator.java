package com.kejin.operator;


import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.var.Var;

import static com.kejin.enums.OperatorsPriority.LEVEL_5;

/**
 * 比较计算福
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
    public void check(Var left, Var right) throws CompileException {
        if (!supportBoolean() && left.valueType() == ValueType.BOOLEAN) {
            throw new CompileException("比较表达式左右值非法" + left + this + right);
        }
        if (!supportText() && left.valueType() == ValueType.TEXT) {
            throw new CompileException("算符不支持文本计算" + left + this + right);
        }
    }
}
