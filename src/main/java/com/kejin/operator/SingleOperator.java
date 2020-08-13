package com.kejin.operator;


import com.kejin.enums.CompileException;
import com.kejin.enums.Lexical;
import com.kejin.enums.ValueType;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.Map;
import java.util.function.Function;

import static com.kejin.enums.OperatorsPriority.LEVEL_1;

/**
 * 单目计算符
 */
public abstract class SingleOperator extends Operators {
    @Override
    public final Lexical lexical() {
        return Lexical.SIMPLE_OPERATOR;
    }

    public void check(Var right) throws CompileException {
        if (right.valueType() != returnType(right)) {
            throw new CompileException(toString() + "后的值类型不满足期望" + right);
        }
    }

    public abstract ValueType returnType(Var right);

    protected abstract Function<Value, Value> calculateFunction();

    public Value calculate(Var right, Map<String, Value> varMap) {
        Value rightValue = right.fill(varMap);
        if (!rightValue.isSuccess()) {
            return rightValue;
        }
        return calculateFunction().apply(rightValue);
    }

    @Override
    public final int priority() {
        return LEVEL_1;
    }
}
