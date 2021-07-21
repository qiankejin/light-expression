package com.kejin.express;


import com.kejin.enums.ValueType;
import com.kejin.operator.DoubleOperators;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.Map;
import java.util.Set;

public class BinaryExpress implements Express {
    private final Var left;
    protected final DoubleOperators operators;
    protected final Var right;

    public BinaryExpress(Var left, DoubleOperators operators, Var right) {
        this.left = left;
        this.operators = operators;
        this.right = right;
    }

    @Override
    public void usedArg(Set<String> args) {
        left.usedArg(args);
        right.usedArg(args);
    }

    @Override
    public ValueType valueType() {
        return operators.returnType(left, right);
    }

    @Override
    public Value fill(Map<String, Value> varMap) {
        return operators.calculate(left, right, varMap);
    }

    @Override
    public String toString() {
        return left.toString() + operators.toString() + right.toString();
    }

}
