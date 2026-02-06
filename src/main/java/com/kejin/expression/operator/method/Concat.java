package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.Errors;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.TextValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;
import java.util.stream.Collectors;

public class Concat extends MultiArgMethod {

    @Override
    public String symbol() {
        return "CONCAT";
    }

    @Override
    public void checkArg(List<Var> argList) {
        if (argList == null || argList.isEmpty()) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "concat函数至少需要一个参数");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.TEXT;
    }

    @Override
    public TextValue calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        return Value.of(args.stream().map(s -> s.execute(param))
                .map(Value::toString).collect(Collectors.joining()));

    }

}
