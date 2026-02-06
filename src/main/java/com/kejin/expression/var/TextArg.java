package com.kejin.expression.var;


/**
 * 文本参数类，表示表达式中的一个文本类型的参数变量
 * 继承自Arg类并实现TextVar接口，专门处理文本类型的参数
 */
public class TextArg extends Arg implements TextVar {

    private TextArg(String code) {
        super(code);
    }

    public static TextArg of(String code) {
        return compileCache.getWithInit(code, TextArg::new, TextArg.class);
    }

}
