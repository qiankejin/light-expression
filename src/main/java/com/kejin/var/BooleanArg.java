package com.kejin.var;


public class BooleanArg extends Arg implements BooleanVar {

    private BooleanArg(String code) {
        super(code);
    }

    public static BooleanArg of(String code) {
        return compileCache.getWithInit(code, BooleanArg::new);
    }

}
