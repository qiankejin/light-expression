package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Value;

/**
 * @author pl
 * @since 2024/7/5 10:32
 */
public class GFirstMethod extends WindowOperators {

    @Override
    public String symbol() {
        return "GFIRST";
    }

    @Override
    protected void checkValueType(ValueType valueType) {

    }

    @Override
    protected WindowFunction getWindowFunction() {
        return new FirstFunction();
    }

    public static class FirstFunction implements WindowFunction {
        private Value<?> first;

        @Override
        public void accept(Value<?> value) {
            if (first == null) {
                first = value;
            }
        }

        @Override
        public Value<?> get() {
            return first;
        }
    }
}
