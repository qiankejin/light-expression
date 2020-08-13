package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.function.Function;

public class Max extends MethodOperators {

    @Override
    public String symbol() {
        return "max";
    }

    @Override
    public void check(List<Var> argList) throws CompileException {
        for (Var express : argList) {
            if (express.valueType() != ValueType.NUMBER) {
                throw new CompileException("最大值函数入参必须是数值");
            }
        }
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return s -> {
            Double max = null;
            Value maxValue = null;
            for (Value value : s) {
                if (max == null) {
                    max = value.toDouble();
                    maxValue = value;
                } else {
                    if (value.toDouble() > max) {
                        max = value.toDouble();
                        maxValue = value;
                    }
                }
            }
            return maxValue;
        };
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokersExpress) {
        return ValueType.NUMBER;
    }
}
