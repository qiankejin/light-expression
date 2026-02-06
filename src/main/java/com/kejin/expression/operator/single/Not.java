package com.kejin.expression.operator.single;


import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.SingleOperator;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.function.Function;

public class Not extends SingleOperator {

    @Override
    public ValueType returnType(Var right) {
        return ValueType.BOOLEAN;
    }

    @Override
    public Value<?> calculate(Var right, ParamCollection param) {
        return Value.of(!right.execute(param).toBoolean());
    }

    @Override
    public String symbol() {
        return "!";
    }
}
