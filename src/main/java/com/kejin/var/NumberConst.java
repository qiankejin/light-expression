package com.kejin.var;


import com.kejin.value.Value;

import java.util.Map;

public class NumberConst extends Const implements NumberVar {

    private NumberConst(String value) {
        try {
            if (value.contains(".")) {
                this.value = Value.of(Double.parseDouble(value));
            } else {
                this.value = Value.of(Integer.parseInt(value));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(value + "数字格式错误");
        }
    }


    public static NumberConst of(String value) {
        return compileCache.getWithInit(value, NumberConst::new);
    }


    @Override
    public Value fill(Map<String, Value> varMap) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
