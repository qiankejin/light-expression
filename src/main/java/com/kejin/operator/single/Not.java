package com.kejin.operator.single;


import com.kejin.enums.ValueType;
import com.kejin.operator.SingleOperator;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.function.Function;

public class Not extends SingleOperator {

    @Override
    public ValueType returnType(Var right) {
        return ValueType.BOOLEAN;
    }

    @Override
    protected Function<Value, Value> calculateFunction() {
        return v -> Value.of(!v.toBoolean());
    }

    @Override
    public String symbol() {
        return "!";
    }
}
