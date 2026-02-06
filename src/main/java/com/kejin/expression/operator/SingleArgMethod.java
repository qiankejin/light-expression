package com.kejin.expression.operator;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CommaExpress;
import com.kejin.expression.express.SingleBrokerExpress;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public abstract class SingleArgMethod extends MethodOperators {

    @Override
    public final void check(BrokersExpress brokersExpress) throws ExpressionException {
        if (!(brokersExpress instanceof SingleBrokerExpress)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, symbol() + "函数只接受一个参数");
        }
        Var singleArg = ((SingleBrokerExpress) brokersExpress).getSingleArg();
        checkArg(singleArg);
    }

    protected abstract void checkArg(Var arg) throws ExpressionException;


    @Override
    public final Value<?> calculate(BrokersExpress node, ParamCollection param) {
        return methodCalculate(((SingleBrokerExpress) node).getSingleArg(), param);
    }

    protected abstract Value<?> methodCalculate(Var arg, ParamCollection param);

}
