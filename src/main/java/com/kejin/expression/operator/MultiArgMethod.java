package com.kejin.expression.operator;

import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.var.Var;

import java.util.List;

public abstract class MultiArgMethod extends MethodOperators {
    @Override
    public final void check(BrokersExpress brokersExpress) throws ExpressionException {
        checkArg(brokersExpress.getArgList());
    }

    protected abstract void checkArg(List<Var> argList) throws ExpressionException;


}
