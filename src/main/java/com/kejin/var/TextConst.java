package com.kejin.var;

import com.kejin.value.Value;

import java.util.Map;

public class TextConst extends Const implements TextVar {

    private TextConst(String value) {
        this.value = Value.ofText(value);
    }

    public static TextConst of(String value) {
        return compileCache.getWithInit(value, TextConst::new);
    }


    @Override
    public Value fill(Map<String, Value> varMap) {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
