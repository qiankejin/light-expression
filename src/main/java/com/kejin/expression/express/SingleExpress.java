package com.kejin.expression.express;


import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.SingleOperator;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

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
    public Value execute(ParamCollection param) {
        return operators.calculate(right, param);
    }

    @Override
    public String toString() {
        return operators.toString() + right.toString();
    }

}
