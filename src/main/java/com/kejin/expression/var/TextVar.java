package com.kejin.expression.var;


import com.kejin.expression.enums.ValueType;

/**
 * 文本变量接口，表示表达式中的一个文本类型的变量节点
 * 扩展自Var接口，专门处理文本类型的变量
 */
public interface TextVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.TEXT;
    }
}
