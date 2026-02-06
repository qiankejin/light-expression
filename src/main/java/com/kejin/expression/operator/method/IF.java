package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class IF extends MultiArgMethod {

    @Override
    public void checkArg(List<Var> argList) throws ExecuteException {
        if (argList.size() != 3) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "if表达式参数必须是三个");
        }
        Var bool = argList.get(0);
        Var tru = argList.get(1);
        Var fal = argList.get(2);
        if (!bool.valueType().accept(ValueType.BOOLEAN)) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "if表达式第一个参数必须是布尔表达式");
        }
        if (!tru.valueType().accept(fal.valueType())) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "if表达式二三参数类型必须相同");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return brokers.getArgList().get(1).valueType();
    }


    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> argList = node.getArgList();
        Var bool = argList.get(0);
        Var tru = argList.get(1);
        Var fal = argList.get(2);
        Value<?> value = bool.execute(param);
        return value.toBoolean() ? tru.execute(param) : fal.execute(param);
    }


    @Override
    public String symbol() {
        return "IF";
    }

}