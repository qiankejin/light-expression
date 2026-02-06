package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public class Length extends SingleArgMethod {
    @Override
    protected void checkArg(Var arg) throws ExpressionException {
    }

    @Override
    protected LongValue methodCalculate(Var arg, ParamCollection param) {
        String strValue = arg.execute(param).toString();
        return Value.of(strValue.codePointCount(0,strValue.length()));
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    public String symbol() {
        return "LEN";
    }
}
