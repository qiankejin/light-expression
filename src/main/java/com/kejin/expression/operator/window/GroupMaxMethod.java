package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Value;

public class GroupMaxMethod extends WindowOperators {
    @Override
    public String symbol() {
        return "GMAX";
    }

    @Override
    protected void checkValueType(ValueType valueType) {

    }

    @Override
    protected WindowFunction getWindowFunction() {
        return new MaxFunction();
    }

    public static class MaxFunction implements WindowFunction {
        private Value<?> max;

        @Override
        public void accept(Value<?> value) {
            if (max == null) {
                max = value;
            } else {
                if (max.compareTo(value) > 0) {
                    max = value;
                }
            }
        }

        @Override
        public Value<?> get() {
            return max;
        }
    }
}
