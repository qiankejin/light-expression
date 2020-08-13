package com.kejin.operator.single;

import com.kejin.enums.ValueType;
import com.kejin.operator.SingleOperator;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.function.Function;

public class BitNot extends SingleOperator {

    @Override
    protected Function<Value, Value> calculateFunction() {
        return v -> Value.of(~v.toInt());
    }

    @Override
    public ValueType returnType(Var right) {
        return ValueType.NUMBER;
    }

    @Override
    public String symbol() {
        return "~";
    }
}
