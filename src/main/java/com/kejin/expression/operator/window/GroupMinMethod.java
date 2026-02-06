package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Value;

public class GroupMinMethod extends WindowOperators {
    @Override
    public String symbol() {
        return "GMIN";
    }

    @Override
    protected void checkValueType(ValueType valueType) {

    }

    @Override
    protected WindowFunction getWindowFunction() {
        return new MinFunction();
    }

    public static class MinFunction implements WindowFunction {
        private Value<?> min;

        @Override
        public void accept(Value<?> value) {
            if (min == null) {
                min = value;
            } else {
                if (min.compareTo(value) < 0) {
                    min = value;
                }
            }
        }

        @Override
        public Value<?> get() {
            return min;
        }
    }
}
