package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;


public class HalfUp extends MultiArgMethod {
    @Override
    public String symbol() {
        return "HALF_UP";
    }


    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        if (!(argList.size() == 1 || argList.size() == 2)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "HalfUp函数入参必须是1个或者两个");
        }
        if (argList.get(0).valueType() != ValueType.NUMBER) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "HalfUp函数第一个入参必须是数值");
        }
        if (argList.size() == 2) {
            if (argList.get(0).valueType() != ValueType.NUMBER) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "HalfUp函数第二个入参必须是数值");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        Value<?> value = args.get(0).execute(param);
        if (args.size() == 1) {
            return Value.of(value.toMath().round());
        } else {
            return Value.of(value.toMath().round((int) args.get(1).execute(param).toLong()));
        }
    }

}
