package com.kejin.expression.operator;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public abstract class DoubleOperators extends Operators {

    @Override
    public final Lexical lexical() {
        return Lexical.OPERATOR;
    }

    public void check(Var left, Var right) throws ExpressionException {
        ValueType returnType = returnType(left, right);
        if (!left.valueType().accept(returnType) || !right.valueType().accept(returnType)) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "双目表达式左右值非法 " + left + this + right);
        }
    }

    public abstract ValueType returnType(Var left, Var right);

    protected abstract Value<?> operatorCalculate(Value<?> left, Value<?> right);

    public Value<?> calculate(Var left, Var right, ParamCollection param) {
        Value<?> leftValue = left.execute(param);
        Value<?> rightValue = right.execute(param);
        return operatorCalculate(leftValue, rightValue);
    }
}
