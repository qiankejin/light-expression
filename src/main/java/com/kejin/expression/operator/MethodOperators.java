package com.kejin.expression.operator;

import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CommaExpress;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Collections;
import java.util.List;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_0;

public abstract class MethodOperators extends Operators {

    @Override
    public int priority() {
        return LEVEL_0;
    }

    public abstract void check(BrokersExpress argList) throws ExpressionException;


    public abstract ValueType methodReturnType(BrokersExpress brokers);

    public abstract Value<?> calculate(BrokersExpress node, ParamCollection param);

    @Override
    public Lexical lexical() {
        return Lexical.METHOD;
    }
}