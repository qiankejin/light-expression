package com.kejin.expression.operator.calculate;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Arg;
import com.kejin.expression.var.Var;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_12;

public class Eval extends CalculateOperator {

    @Override
    public Value<?> calculate(Var left, Var right, ParamCollection varMap) {
        Value<?> leftValue = right.execute(varMap);
        varMap.put(left.toString(), leftValue);
        return leftValue;
    }

    @Override
    protected Value<?> operatorCalculate(Value<?> left, Value<?> right) {
        return null;
    }

    @Override
    public void check(Var left, Var right) throws ExpressionException {
        if (!(left instanceof Arg)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR,"赋值表达式左边必须是变量" + left + this + right);
        }
        if (left.valueType() != right.valueType()) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR,"赋值表达式左右类型必须相同" + left + this + right);
        }
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
