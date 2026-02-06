package com.kejin.expression.var;

import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;

public class BooleanConst extends Const implements BooleanVar {

    public static final BooleanConst TRUE_CONST = new BooleanConst(BooleanValue.TRUE);
    public static final BooleanConst FALSE_CONST = new BooleanConst(BooleanValue.FALSE);

    private BooleanConst(BooleanValue value) {
        this.value = value;
    }


    @Override
    public Value<?> execute(ParamCollection param) {
        return value;
    }

    @Override
    public String toString() {
        return value.toBoolean() ? "true" : "false";
    }
}
