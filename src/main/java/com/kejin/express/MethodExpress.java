package com.kejin.express;

import com.kejin.enums.ValueType;
import com.kejin.operator.MethodOperators;
import com.kejin.value.Value;

import java.util.Map;
import java.util.Set;

public class MethodExpress implements Express{
    protected MethodOperators operators;
    protected BrokersExpress right;

    public MethodExpress(MethodOperators operator,BrokersExpress right) {
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
    public Value fill(Map<String, Value> varMap) {
        return operators.calculate(right, varMap);
    }

    @Override
    public String toString() {
        return operators.toString() + right.toString();
    }

}
