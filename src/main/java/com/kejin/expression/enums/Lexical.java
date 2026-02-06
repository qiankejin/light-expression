package com.kejin.expression.enums;

/**
 * 词法
 */
public enum Lexical {
    /**
     * 多操作符
     */
    OPERATOR,
    /**
     * 左括号
     */
    BLOCKERS_LEFT,
    /**
     * 右括号
     */
    BLOCKERS_RIGHT,
    /**
     * 变量
     */
    ARG,
    /**
     * 单操作符
     */
    SIMPLE_OPERATOR,
    /**
     * 参数列表
     */
    ARG_LIST,

    /**
     * 函数
     */
    METHOD;

}
