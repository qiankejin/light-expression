package com.kejin.expression.enums;

/**
 * 词法分析状态
 */
public enum LexicalState {
    /**
     * 初始状态
     */
    START,
    /**
     * 变量规约
     */
    ARG_REDUCTION,
    /**
     * 数字常量规约
     */
    NUMBER_REDUCTION,

    /**
     * 字符串常量规约
     */
    TEXT_REDUCTION,
    /**
     * 操作符规约
     */
    OPERATOR_REDUCTION
}
