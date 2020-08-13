package com.kejin.operator.method;


import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.SingleArgMethod;
import com.kejin.value.Decimal;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.function.Function;


public class Cos extends SingleArgMethod {

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    protected void checkArg(Var arg) throws CompileException {
        if (arg.valueType() != ValueType.NUMBER) {
            throw new CompileException("cos函数入参必须是数值");
        }
    }

    @Override
    protected Function<Value, Value> calculateSingle() {
        return v -> Value.of(Decimal.of(Math.cos(v.toMath().multiply(Math.PI).divide(180).toDouble())));
    }

    @Override
    public String symbol() {
        return "cos";
    }
}
