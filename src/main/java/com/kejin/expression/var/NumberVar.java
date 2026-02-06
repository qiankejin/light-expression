package com.kejin.expression.var;


import com.kejin.expression.enums.ValueType;

/**
 * 数字变量接口，表示表达式中的一个数字类型的变量节点
 * 扩展自Var接口，专门处理数字类型的变量
 */
public interface NumberVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.NUMBER;
    }
}
