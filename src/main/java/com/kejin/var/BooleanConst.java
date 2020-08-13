package com.kejin.var;

import com.kejin.value.BooleanValue;
import com.kejin.value.Value;

import java.util.Map;

public class BooleanConst extends Const implements BooleanVar {

    private static final BooleanConst TRUE_CONST = new BooleanConst(BooleanValue.TRUE);
    private static final BooleanConst FALSE_CONST = new BooleanConst(BooleanValue.FALSE);

    private BooleanConst(BooleanValue value) {
        this.value = value;
    }

    public static BooleanConst of(String value) {
        if (Boolean.parseBoolean(value)) {
            return TRUE_CONST;
        } else {
            return FALSE_CONST;
        }
    }

    @Override
    public Value fill(Map<String, Value> varMap) {
        return value;
    }

    @Override
    public String toString() {
        return value.toBoolean() ? "true" : "false";
    }
}
