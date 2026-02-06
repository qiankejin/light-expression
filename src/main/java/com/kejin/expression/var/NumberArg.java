package com.kejin.expression.var;


public class NumberArg extends Arg implements NumberVar {

    public NumberArg(String code) {
        super(code);
    }

    public static NumberArg of(String code) {
        return compileCache.getWithInit(code, NumberArg::new, NumberArg.class);
    }


}
