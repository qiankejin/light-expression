package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.SingleArgMethod;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.function.Function;


public class HalfUp extends SingleArgMethod {
    @Override
    public String symbol() {
        return "HALFUP";
    }

    @Override
    protected void checkArg(Var arg)  {
        if (arg.valueType() != ValueType.NUMBER) {
            throw new CompileException("HalfUp函数入参必须是数值");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }


    @Override
    protected Function<Value, Value> calculateSingle() {
        return v -> Value.of(v.toMath().round());
    }
}
