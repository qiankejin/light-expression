package com.kejin.expression.operator.window;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.WindowOperators;
import com.kejin.expression.value.Decimal;
import com.kejin.expression.value.Value;

/**
 * 平均值函数类，用于计算数值的平均值
 * 该类继承自WindowOperators，用于窗口函数计算场景
 */
public class AverageMethod extends WindowOperators {
    /**
     * 返回平均值函数的符号
     *
     * @return AVG，表示平均值函数
     */
    @Override
    public String symbol() {
        return "AVG";
    }

    /**
     * 检查值类型是否为数值类型
     *
     * @param valueType 要检查的值类型
     * @throws ExpressionException 当值类型不是数值类型时抛出异常
     */
    @Override
    protected void checkValueType(ValueType valueType) throws ExpressionException {
        if (valueType != ValueType.NUMBER) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "AVG函数入参必须是数值");
        }
    }

    /**
     * 获取平均值计算函数实例
     *
     * @return AverageFunction实例，用于执行平均值计算
     */
    @Override
    protected WindowFunction getWindowFunction() {
        return new AverageFunction();
    }

    /**
     * 平均值计算函数内部类
     * 用于实现具体的平均值计算逻辑
     */
    public static class AverageFunction implements WindowFunction {
        // 用于累计所有数值的总和
        private final Decimal total = Decimal.of(0);
        // 用于记录数值的数量
        private int count;

        @Override
        public void accept(Value<?> value) {
            // 累加数值到总计中
            total.plus(value.toMath());
            // 增加计数
            count++;
        }

        @Override
        public Value<?> get() {
            // 计算平均值（总和除以计数）并返回
            return Value.of(total.divide(count));
        }
    }
}
