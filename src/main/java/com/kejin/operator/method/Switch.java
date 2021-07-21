package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.express.CaseExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Value;
import com.kejin.var.Arg;
import com.kejin.var.Const;
import com.kejin.var.Var;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class Switch extends MethodOperators {

    @Override
    public void check(List<Var> argList) {
        Var switchArg = argList.get(0);
        if (!(switchArg instanceof Arg)) {
            throw new CompileException("switch函数第一个参数必须是变量");
        }
        ValueType returnType = null;
        for (int i = 1; i < argList.size(); i++) {
            Var a = argList.get(i);
            if ((!(a instanceof CaseExpress))) {
                throw new CompileException("switch函数其他参数必须是case表达式");
            }
            if (switchArg.valueType() != ((CaseExpress) a).getWhenVar().valueType()) {
                throw new CompileException("switch函数的case表达式类型必须相同");
            }
            if (returnType == null) {
                returnType = a.valueType();
            } else if (a.valueType() != returnType) {
                throw new CompileException("switch函数的case表达式返回类型必须相同");

            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return brokers.getArgList().get(0).valueType();
    }

    public static final String SWITCH_TEST = "SWITCH_TEST_FLAG";

    @Override
    public Value calculate(BrokersExpress node, Map<String, Value> varMap) {
        List<Var> argList = node.getArgList();
        Arg switchVar = (Arg) argList.get(0);
        Value value = varMap.get(SWITCH_TEST);
        if (value != null && value.toBoolean()) {
            CaseExpress var = (CaseExpress) argList.get(new Random().nextInt(argList.size() - 2) + 1);
            Const whenVar = var.getWhenVar();
            Var thenVar = var.getThenVar();
            varMap.put(switchVar.toString(), whenVar.fill(varMap));
            return thenVar.fill(varMap);
        }
        for (int i = 1; i < argList.size(); i++) {
            CaseExpress ca = ((CaseExpress) argList.get(i));
            ca.setSwitchArg(switchVar);
            Value fill = ca.fill(varMap);
            if (fill != null) {
                return fill;
            }
        }
        return null;
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return null;
    }

    @Override
    public String symbol() {
        return "SWITCH";
    }
}
