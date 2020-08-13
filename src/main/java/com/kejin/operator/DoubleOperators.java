package com.kejin.operator;

import com.kejin.enums.CompileException;
import com.kejin.enums.Lexical;
import com.kejin.enums.ValueType;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class DoubleOperators extends Operators {

    @Override
    public final Lexical lexical() {
        return Lexical.OPERATOR;
    }

    public void check(Var left, Var right) throws CompileException {
        if (left.valueType() != returnType(left,right) || right.valueType() != returnType(left,right)) {
            throw new CompileException("双目表达式左右值非法" + left + this + right);
        }
    }

    public abstract ValueType returnType(Var left, Var right);

    protected abstract BiFunction<Value, Value, Value> calculateFunction();

    public Value calculate(Var left, Var right, Map<String, Value> varMap) {
        Value leftValue = left.fill(varMap);
        if (!leftValue.isSuccess()) {
            return leftValue;
        }
        Value rightValue = right.fill(varMap);
        if (!rightValue.isSuccess()) {
            return rightValue;
        }
        return calculateFunction().apply(leftValue, rightValue);
    }
}
