package com.kejin.expression.operator.window;

import com.kejin.expression.value.Value;

/**
 * 窗口函数接口，定义窗口操作的基本功能
 * 用于处理流式数据的窗口聚合计算
 */
public interface WindowFunction {
    /**
     * 接收并处理输入值
     * @param value 输入的值对象
     */
    void accept(Value<?> value);

    /**
     * 获取窗口计算的结果值
     * @return 窗口聚合计算的结果
     */
    Value<?> get();
}
