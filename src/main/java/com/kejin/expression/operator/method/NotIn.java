package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class NotIn extends MultiArgMethod {

    @Override
    public String symbol() {
        return "NOT_IN";
    }

    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        if (argList.size() < 2) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "NOT_IN函数参数个数必须大于2");
        }
        ValueType type = null;
        for (Var express : argList) {
            if (type == null) {
                type = express.valueType();
            } else if (!type.accept(express.valueType())) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "NOT_IN函数参数必须是相同类型");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.BOOLEAN;
    }


    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        Value<?> value = args.get(0).execute(param);
        for (int i = 1; i < args.size(); i++) {
            if (value.eq(args.get(i).execute(param))) {
                return BooleanValue.FALSE;
            }
        }
        return BooleanValue.TRUE;
    }

}
