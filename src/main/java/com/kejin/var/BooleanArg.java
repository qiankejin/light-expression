package com.kejin.var;


public class BooleanArg extends Arg implements BooleanVar {

    private BooleanArg(String code) {
        this.code = code;
    }

    public static BooleanArg of(String code) {
        return compileCache.getWithInit(code, BooleanArg::new);
    }

}
