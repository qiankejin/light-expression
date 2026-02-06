package com.kejin.expression.operator.method;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Decimal;
import com.kejin.expression.value.DoubleValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

public class Sqrt extends SingleArgMethod {

    @Override
    public String symbol() {
        return "SQRT";
    }

    @Override
    protected void checkArg(Var arg) throws ExpressionException {
        if (!arg.valueType().accept(ValueType.NUMBER)) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "sqrt函数入参必须是数值");
        }
    }


    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    protected DoubleValue methodCalculate(Var arg, ParamCollection param) {
        return Value.of(Decimal.of(Math.sqrt(arg.execute(param).toDouble())));
    }

}
