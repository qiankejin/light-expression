package com.kejin.operator.calculate;


import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.operator.CalculateOperator;
import com.kejin.value.Value;
import com.kejin.var.Arg;
import com.kejin.var.Var;

import java.util.Map;
import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_12;

public class Eval extends CalculateOperator {

    @Override
    public Value calculate(Var left, Var right, Map<String, Value> varMap) {
        Value leftValue = right.fill(varMap);
        if (!leftValue.isSuccess()) {
            return leftValue;
        }
        varMap.put(left.toString(), leftValue);
        return leftValue;
    }


    @Override
    public void check(Var left, Var right) throws CompileException {
        if (!(left instanceof Arg)) {
            throw new CompileException("赋值表达式左边必须是变量" + left + this + right);
        }
        if (left.valueType() != right.valueType()) {
            throw new CompileException("赋值表达式左右类型必须相同" + left + this + right);
        }
    }

    @Override
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return null;
    }

    @Override
    public ValueType returnType(Var left, Var right) {
        return left.valueType();
    }

    @Override
    public int priority() {
        return LEVEL_12;
    }

    @Override
    public String symbol() {
        return "=";
    }
}
