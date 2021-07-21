package com.kejin.enums;

/**
 * 词法分析状态
 */
public enum LexicalState {
    START,
    /**
     * 变量规约
     */
    ARG_REDUCTION,
    /**
     * 常量规约
     */
    CONST_REDUCTION,

    /**
     * 字符串常量规约
     */
    TEXT_REDUCTION,
    /**
     * 操作符规约
     */
    OPERATOR_REDUCTION
}
