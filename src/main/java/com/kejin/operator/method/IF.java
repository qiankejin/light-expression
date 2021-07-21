package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class IF extends MethodOperators {

    @Override
    public void check(List<Var> argList)  {
        if (argList.size() != 3) {
            throw new CompileException("if表达式参数必须是三个");
        }
        Var bool = argList.get(0);
        Var tru = argList.get(1);
        Var fal = argList.get(2);
        if (bool.valueType() != ValueType.BOOLEAN) {
            throw new CompileException("if表达式第一个参数必须是布尔表达式");
        }
        if (tru.valueType() != fal.valueType()) {
            throw new CompileException("if表达式二三参数类型必须相同");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return brokers.getArgList().get(1).valueType();
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return null;
    }

    @Override
    public Value calculate(BrokersExpress node, Map<String, Value> varMap) {
        List<Var> argList = node.getArgList();
        Var bool = argList.get(0);
        Var tru = argList.get(1);
        Var fal = argList.get(2);
        Value value = bool.fill(varMap);
        if (!value.isSuccess()) {
            return value;
        }
        return value.toBoolean() ? tru.fill(varMap) : fal.fill(varMap);
    }


    @Override
    public String symbol() {
        return "IF";
    }

}
