package com.kejin.operator.method;


import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Decimal;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.function.Function;

public class Power extends MethodOperators {


    @Override
    public void check(List<Var> argList)  {
        if (argList.size() != 2) {
            throw new CompileException("pow表达式参数必须是两个");
        }
        Var left = argList.get(0);
        Var right = argList.get(1);
        if (left.valueType() != ValueType.NUMBER || right.valueType() != ValueType.NUMBER) {
            throw new CompileException("pow表达式参数必须是数字");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return null;
    }

    @Override
    public String symbol() {
        return "POW";
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return (v) -> {
            if (v.get(0).isDouble() || v.get(1).isDouble()) {
                return Value.of(Decimal.of(Math.pow(v.get(0).toDouble(), v.get(1).toDouble())));
            }
            return Value.of((int) Math.pow(v.get(0).toInt(), v.get(1).toInt()));
        };
    }

}
