package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.TextValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public class ToString extends SingleArgMethod {
    @Override
    protected void checkArg(Var arg) throws ExpressionException {

    }

    @Override
    protected TextValue methodCalculate(Var arg, ParamCollection param) {
        return Value.of(arg.execute(param).toString());
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.TEXT;
    }

    @Override
    public String symbol() {
        return "TO_STR";
    }
}
