package com.kejin.expression.operator.method;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.TwoArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Decimal;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class Power extends TwoArgMethod {


    @Override
    public void checkArg(Var argOne, Var argTwo) throws ExpressionException {
        if (!argOne.valueType().accept(ValueType.NUMBER) || !argTwo.valueType().accept(ValueType.NUMBER)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "pow表达式参数必须是数字");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    public String symbol() {
        return "POW";
    }

    @Override
    protected Value<?> methodCalculate(Var argOne, Var argTwo, ParamCollection param) {
        Value<?> left = argOne.execute(param);
        Value<?> right = argTwo.execute(param);
        if (left.isDouble() || right.isDouble()) {
            return Value.of(Decimal.of(Math.pow(left.toDouble(), right.toDouble())));
        }
        return Value.of((int) Math.pow(left.toInt(), right.toInt()));
    }

}
