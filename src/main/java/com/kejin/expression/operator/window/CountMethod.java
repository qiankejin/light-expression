package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Value;

public class CountMethod extends WindowOperators {

    @Override
    protected void checkValueType(ValueType valueType) {
    }


    @Override
    public String symbol() {
        return "COUNT";
    }

    @Override
    protected WindowFunction getWindowFunction() {
        return new CountFunction();
    }

    public static class CountFunction implements WindowFunction {
        private int total;

        @Override
        public void accept(Value value) {
            total++;
        }

        @Override
        public Value get() {
            return Value.of(total);
        }
    }


}
