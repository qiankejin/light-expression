package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.DoubleValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public class Inverse extends SingleArgMethod {
    @Override
    protected void checkArg(Var arg) throws ExpressionException {
        if (arg.valueType() != ValueType.NUMBER) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "INVER函数入参必须是数字");
        }
    }

    @Override
    protected Value<?> methodCalculate(Var arg, ParamCollection param) {
        Value<?> value = arg.execute(param);
        if (value.isDouble()) {
            return Value.of(value.toMath().multiply(-1));
        }
        return Value.of(value.toLong() * -1);
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    public String symbol() {
        return "INVER";
    }
}
