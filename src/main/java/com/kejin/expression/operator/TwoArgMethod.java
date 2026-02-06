package com.kejin.expression.operator;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CommaBrokerExpress;
import com.kejin.expression.express.CommaExpress;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public abstract class TwoArgMethod extends MethodOperators {
    @Override
    public final void check(BrokersExpress brokersExpress) throws ExpressionException {

        if (!(brokersExpress instanceof CommaBrokerExpress)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, symbol() + "函数的两个参数必填");
        }
        List<Var> argList = brokersExpress.getArgList();
        if (argList.size() != 2) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, symbol() + "函数只接受两个参数");
        }
        checkArg(argList.get(0), argList.get(1));
    }

    protected abstract void checkArg(Var argOne, Var argTwo) throws ExpressionException;

    @Override
    public final Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        return methodCalculate(args.get(0), args.get(1), param);
    }


    protected abstract Value<?> methodCalculate(Var argOne, Var argTwo, ParamCollection param);
}
