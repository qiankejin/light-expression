package com.kejin.expression.express;


import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.DoubleOperators;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Set;
/**
 * 二元表达式
 * */
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
    public Value<?> execute(ParamCollection param) {
        return operators.calculate(left, right, param);
    }

    @Override
    public String toString() {
        return left.toString() + operators.toString() + right.toString();
    }

}
