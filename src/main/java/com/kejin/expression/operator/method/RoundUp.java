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


public class RoundUp extends MultiArgMethod {

    @Override
    public String symbol() {
        return "ROUND_UP";
    }


    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        if (argList.size() > 2) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "RoundUp函数只接受两个数值参数");
        }
        if (!argList.get(0).valueType().accept(ValueType.NUMBER)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "RoundUp函数第一个参数必须是数值");
        }
        if (argList.size() == 2) {
            if (!argList.get(1).valueType().accept(ValueType.NUMBER)) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "RoundUp函数第二个参数必须是数值");
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
        if (args.size() == 1) {
            return Value.of(args.get(0).execute(param).toMath().upScale());
        } else {
            return Value.of(args.get(0).execute(param).toMath().upScale(args.get(1).execute(param).toInt()));
        }
    }
}
