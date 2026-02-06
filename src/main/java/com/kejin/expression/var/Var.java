package com.kejin.expression.var;


import com.kejin.expression.Node;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.ExpressionCache;
import com.kejin.expression.value.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * 变量接口，表示表达式中的一个变量节点
 * 扩展自Node接口，提供变量相关的操作方法
 */
public interface Var extends Node {
    /**
     * 编译缓存，用于存储编译结果以提高性能
     */
    ExpressionCache compileCache = new ExpressionCache(1024);

    @Override
    default Lexical lexical() {
        return Lexical.ARG;
    }

    /**
     * 获取变量的值类型
     *
     * @return 变量的ValueType
     */
    ValueType valueType();

    /**
     * 执行变量获取其值
     *
     * @param param 参数集合，包含执行时所需的参数
     * @return 变量的计算结果值
     */
    Value<?> execute(ParamCollection param);

    /**
     * 收集变量使用到的参数名称
     *
     * @param args 用于存放被使用的参数名的集合
     */
    void usedArg(Set<String> args);

    @Override
    String toString();
}
