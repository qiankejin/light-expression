package com.kejin.expression.var;

import com.kejin.expression.enums.ExTreeCache;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;

/**
 * 文本常量类，表示表达式中的一个文本类型的常量变量
 * 继承自Const类并实现TextVar接口，专门处理文本类型的常量
 */
public class TextConst extends Const implements TextVar {

    private TextConst(String value) {
        this.value = Value.of(value);
    }

    public static TextConst of(String value) {
        return compileCache.getWithInit(value, TextConst::new, TextConst.class);
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        return value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
