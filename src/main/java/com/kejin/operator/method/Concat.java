package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Concat extends MethodOperators {

    @Override
    public String symbol() {
        return "concat";
    }

    @Override
    public void check(List<Var> argList) throws CompileException {
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.TEXT;
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return v->Value.of(v.stream().map(Value::toString).collect(Collectors.joining()));
    }

}
