package com.kejin.var;


public class NumberArg extends Arg implements NumberVar {

    private NumberArg(String code) {
        this.code = code;
    }

    public static NumberArg of(String code) {
        return compileCache.getWithInit(code,NumberArg::new);
    }


}
