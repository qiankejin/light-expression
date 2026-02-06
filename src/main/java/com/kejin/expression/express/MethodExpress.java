package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;

import java.util.Set;

public class MethodExpress implements Express {
    protected MethodOperators operators;
    protected BrokersExpress right;

    public MethodExpress(MethodOperators operator, BrokersExpress right) {
        this.operators = operator;
        this.right = right;
    }

    @Override
    public void usedArg(Set<String> args) {
        right.usedArg(args);
    }

    @Override
    public ValueType valueType() {
        return operators.methodReturnType(right);
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        return operators.calculate(right, param);
    }

    @Override
    public String toString() {
        return operators.toString() + right.toString();
    }

}
