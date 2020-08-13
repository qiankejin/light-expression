package com.kejin.operator.method;


import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.SingleArgMethod;
import com.kejin.value.Decimal;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.function.Function;

public class Sqrt extends SingleArgMethod {

    @Override
    public String symbol() {
        return "sqrt";
    }

    @Override
    protected void checkArg(Var arg) throws CompileException {
        if (arg.valueType() != ValueType.NUMBER) {
            throw new CompileException("sqrt函数入参必须是数值");
        }
    }


    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    protected Function<Value, Value> calculateSingle() {
        return v -> Value.of(Decimal.of(Math.sqrt(v.toDouble())));
    }
}
