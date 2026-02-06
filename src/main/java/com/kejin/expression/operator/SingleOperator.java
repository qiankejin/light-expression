package com.kejin.expression.operator;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.function.Function;

import static com.kejin.expression.enums.OperatorsPriority.LEVEL_1;

/**
 * 单目计算符
 */
public abstract class SingleOperator extends Operators {
    @Override
    public final Lexical lexical() {
        return Lexical.SIMPLE_OPERATOR;
    }

    public void check(Var right)  {
        if (!right.valueType().accept( returnType(right))) {
             throw new ExecuteException(ErrorCode.GRAMMAR_ERROR,toString() + "后的值类型不满足期望" + right);
        }
    }

    public abstract ValueType returnType(Var right);



    public abstract Value<?> calculate(Var right, ParamCollection param);

    @Override
    public final int priority() {
        return LEVEL_1;
    }
}
