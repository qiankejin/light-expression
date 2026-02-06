package com.kejin.expression;

import com.kejin.expression.enums.Lexical;

import java.io.Serializable;

/**
 * 表达式节点接口
 * 定义了表达式解析树中节点的基本行为和属性
 */
public interface Node {

    /**
     * 获取节点的词法类型
     *
     * @return 词法类型枚举值
     */
    Lexical lexical();

    @Override
    String toString();

}
