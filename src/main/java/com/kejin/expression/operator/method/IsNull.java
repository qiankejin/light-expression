package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.SingleBrokerExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Arg;
import com.kejin.expression.var.Var;

/**
 * @author 钱克金
 */
public class IsNull extends SingleArgMethod {

    @Override
    protected void checkArg(Var arg) throws ExpressionException {
        if (!(arg instanceof Arg)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "判空函数入参必须是变量");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.BOOLEAN;
    }

    @Override
    public BooleanValue methodCalculate(Var arg, ParamCollection param) {
        try {
            arg.execute(param);
            return BooleanValue.FALSE;
        } catch (ExecuteException e) {
            if (e.getCode() == ErrorCode.ARG_MISS.getCode()) {
                return BooleanValue.TRUE;
            } else {
                throw e;
            }
        }
    }

    @Override
    public String symbol() {
        return "IS_NULL";
    }
}
