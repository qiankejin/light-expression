package com.kejin.expression.var;


import com.kejin.expression.enums.ExTreeCache;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;

import java.math.BigDecimal;

public class NumberConst extends Const implements NumberVar {


    private NumberConst(String value) {
        try {
            if (value.contains(".")) {
                this.value = Value.of(new BigDecimal(value));
            } else {
                this.value = Value.of(Integer.parseInt(value));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(value + "数字格式错误");
        }
    }


    public static NumberConst of(String value) {
        return compileCache.getWithInit(value, NumberConst::new, NumberConst.class);
    }


    @Override
    public Value<?> execute(ParamCollection param) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
