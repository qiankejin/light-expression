package com.kejin.expression.operator.calculate;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Const;
import com.kejin.expression.var.Var;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_12;

public class Case extends CalculateOperator {

    @Override
    public void check(Var left, Var right) {
        if (!(left instanceof Const)&&!(left.valueType()== ValueType.BOOLEAN)) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "case左边必须是常量或布尔表达式");
        }
    }

    @Override
    protected Value<?> operatorCalculate(Value<?> left, Value<?> right) {
        return right;
    }


    @Override
    public int priority() {
        return LEVEL_12;
    }

    @Override
    public String symbol() {
        return ":";
    }
}
