package com.kejin.var;


public class TextArg extends Arg implements TextVar {

    private TextArg(String code) {
        super(code);
    }

    public static TextArg of(String code) {
        return compileCache.getWithInit(code, TextArg::new);
    }

}
