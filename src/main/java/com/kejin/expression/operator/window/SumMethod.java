package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Decimal;
import com.kejin.expression.value.Value;

public class SumMethod extends WindowOperators {
    @Override
    public String symbol() {
        return "SUM";
    }

    @Override
    protected void checkValueType(ValueType valueType) throws ExpressionException {
        if (valueType != ValueType.NUMBER) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "SUM函数入参必须是数值");
        }
    }

    @Override
    protected WindowFunction getWindowFunction() {
        return new SumFunction();
    }

    public static class SumFunction implements WindowFunction {
        private final Decimal decimal = Decimal.of(0);

        @Override
        public void accept(Value<?> value) {
            decimal.plus(value.toMath());
        }

        @Override
        public Value<?> get() {
            return Value.of(decimal.copy());
        }
    }
}
