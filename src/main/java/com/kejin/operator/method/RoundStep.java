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

public class RoundStep extends MethodOperators {
    @Override
    public void check(List<Var> argList)  {
        if (argList.size() > 2) {
            throw new CompileException("RoundUp函数只接受两个数值参数");
        }
        if (argList.get(0).valueType() != ValueType.NUMBER) {
            throw new CompileException("RoundUp函数第一个参数必须是数值");
        }
        if (argList.size() == 2) {
            if (argList.get(1).valueType() != ValueType.NUMBER) {
                throw new CompileException("RoundUp函数第二个参数必须是数值");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return v -> {
            if (v.size() == 1) {
                return Value.of(v.get(0).toMath().upScale());
            } else {
                Decimal step = v.get(1).toMath();
                return Value.of(v.get(0).toMath().divide(step).up().multiply(step));
            }
        };
    }


    @Override
    public String symbol() {
        return "ROUNDSTEP";
    }
}
