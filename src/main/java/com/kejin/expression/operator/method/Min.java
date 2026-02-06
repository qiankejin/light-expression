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

public class Min extends MultiArgMethod {

    @Override
    public String symbol() {
        return "MIN";
    }

    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        for (Var express : argList) {
            if (!express.valueType().accept(ValueType.NUMBER)) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "最小值函数入参必须是数值");
            }
        }
    }

    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        Value<?> minValue = null;
        for (Var var : args) {
            Value<?> value = var.execute(param);
            if (minValue == null) {
                minValue = value;
            } else {
                if (value.compareTo(minValue) < 0) {
                    minValue = value;
                }
            }
        }
        return minValue;
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokersExpress) {
        return ValueType.NUMBER;
    }
}
