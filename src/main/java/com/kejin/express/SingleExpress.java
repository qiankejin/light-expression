package com.kejin.express;


import com.kejin.enums.ValueType;
import com.kejin.operator.SingleOperator;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.Map;
import java.util.Set;

public class SingleExpress implements Express {
    protected SingleOperator operators;
    protected Var right;

    public SingleExpress(Var right, SingleOperator operators) {
        this.operators = operators;
        this.right = right;
    }

    @Override
    public void usedArg(Set<String> args) {
        right.usedArg(args);
    }

    @Override
    public ValueType valueType() {
        return operators.returnType(right);
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
