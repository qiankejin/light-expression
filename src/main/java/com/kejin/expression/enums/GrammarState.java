package com.kejin.expression.enums;

/**
 * 语法分析状态
 */
public enum GrammarState {
    /**
     * 起始状态
     */
    START,

    /**
     * 参数
     */
    ARG,
    /**
     * 括号规约
     */
    BROKER_REDUCE,
    /**
     * 双目运算符规约
     */
    OPERATOR_REDUCE,
    /**
     * 双目运算符规约右值
     *
     */
    OPERATOR_ARG,
    /**
     * 单目运算符规约
     */
    SINGLE_REDUCE,
    /**
     * 函数规约
     */
    METHOD_REDUCE,
    /**
     * 参数列表规约
     */
    ARG_LIST_REDUCE,
    ;
}
