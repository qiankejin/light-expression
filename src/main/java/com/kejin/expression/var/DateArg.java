package com.kejin.expression.var;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;

/**
 * @author 钱克金
 */
public class DateArg extends Arg {

    public DateArg(String code) {
        super(code);
    }


    public static DateArg of(String code) {
        return compileCache.getWithInit(code, DateArg::new, DateArg.class);
    }

    @Override
    public ValueType valueType() {
        return ValueType.DATE;
    }
}
