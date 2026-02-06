package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public class IsEmpty extends SingleArgMethod {
    @Override
    protected void checkArg(Var arg) throws ExpressionException {

    }

    @Override
    protected BooleanValue methodCalculate(Var arg, ParamCollection param) {
        try {
            Value<?> value = arg.execute(param);
            if (value == null || value.toString().isEmpty()) {
                return BooleanValue.TRUE;
            } else {
                return BooleanValue.FALSE;
            }
        } catch (ExecuteException e) {
            if (e.getCode() == ErrorCode.ARG_MISS.getCode()) {
                return BooleanValue.TRUE;
            } else {
                throw e;
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.BOOLEAN;
    }

    @Override
    public String symbol() {
        return "IS_EMPTY";
    }
}
