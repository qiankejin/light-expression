package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.TextValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class SubString extends MultiArgMethod {


    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.TEXT;
    }

    @Override
    public TextValue calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        String value = args.get(0).execute(param).toString();
        int start = 0;
        int length = 0;
        if (args.size() == 3) {
            start = args.get(1).execute(param).toInt();
            length = args.get(2).execute(param).toInt();
        } else {
            length = args.get(1).execute(param).toInt();
        }

        return Value.of(value.substring(start, start+length));
    }

    @Override
    public String symbol() {
        return "SUB_STR";
    }

    @Override
    protected void checkArg(List<Var> argList) throws ExpressionException {
        if (argList.size() < 2 || argList.size() > 3) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "SUB_STR函数只接受两个或三个入参");
        }
        if (argList.size() == 3) {
            if (argList.get(2).valueType() != ValueType.NUMBER) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "SUB_STR函数第三个入参必须是数值");
            }
        }
        if (argList.get(1).valueType() != ValueType.NUMBER) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "SUB_STR函数第二个入参必须是数值");

        }
    }
}
