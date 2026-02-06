package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class Max extends MultiArgMethod {

    @Override
    public String symbol() {
        return "MAX";
    }

    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        for (Var express : argList) {
            if (!express.valueType().accept(ValueType.NUMBER)) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "最大值函数入参必须是数值");
            }
        }
    }

    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        Value<?> maxValue = null;
        for (Var var : args) {
            Value<?> value = var.execute(param);
            if (maxValue == null) {
                maxValue = value;
            } else {
                if (value.compareTo(maxValue) > 0) {
                    maxValue = value;
                }
            }
        }
        return maxValue;
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokersExpress) {
        return ValueType.NUMBER;
    }
}
