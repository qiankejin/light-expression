package com.kejin.expression.operator;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.EmptyBrokerExpress;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;

public abstract class NonArgMethod extends MethodOperators {

    @Override
    public final void check(BrokersExpress brokersExpress) throws ExpressionException {
        if (!(brokersExpress instanceof EmptyBrokerExpress)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, symbol() + "函数不接受入参");
        }
    }

    @Override
    public final ValueType methodReturnType(BrokersExpress brokers) {
        return methodReturnType();
    }

    public abstract ValueType methodReturnType();

    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        return methodCalculate();
    }

    protected abstract Value<?> methodCalculate();
}
