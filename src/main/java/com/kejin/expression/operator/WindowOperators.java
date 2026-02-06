package com.kejin.expression.operator;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.SingleBrokerExpress;
import com.kejin.expression.operator.window.WindowFunction;
import com.kejin.expression.param.ListParam;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

/**
 * 窗口函数操作符抽象类
 * 用于处理需要在一组数据上进行聚合计算的函数，如平均值、求和等
 * 窗口函数会在列表数据上迭代并累积计算结果
 */
public abstract class WindowOperators extends SingleArgMethod {

    /**
     * 检查窗口函数参数的有效性
     * 窗口函数只接受一个参数
     *
     * @param arg 参数列表
     * @throws ExpressionException 当参数数量不为1或参数类型不符合要求时抛出
     */
    @Override
    public void checkArg(Var arg) throws ExpressionException {
        // 检查参数值类型是否符合要求
        checkValueType(arg.valueType());
    }

    /**
     * 检查值类型是否符合窗口函数的要求
     * 具体的窗口函数需要实现此方法来验证特定的数据类型
     *
     * @param valueType 要检查的值类型
     * @throws ExpressionException 当值类型不符合要求时抛出
     */
    protected abstract void checkValueType(ValueType valueType) throws ExpressionException;

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        // 窗口函数的返回类型固定为数值类型
        return ValueType.NUMBER;
    }



    @Override
    public Value<?> methodCalculate(Var arg, ParamCollection param) {
        // 验证参数类型是否为ListParam，因为窗口函数需要处理列表数据
        if (!(param instanceof ListParam)) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "窗口函数必须输入列表数据");
        }
        List<MapParam> listData = ((ListParam) param).getListData();
        // 获取窗口函数实现
        WindowFunction windowFunction = getWindowFunction();
        // 遍历列表中的每一项数据，执行计算并累积到窗口函数中
        for (MapParam listDatum : listData) {
            Value<?> value = arg.execute(listDatum);
            windowFunction.accept(value);
        }
        // 返回最终的计算结果
        return windowFunction.get();
    }

    /**
     * 获取窗口函数实现
     * 子类需要实现此方法来提供具体的窗口函数计算逻辑
     *
     * @return WindowFunction的实例，用于执行具体的窗口计算
     */
    protected abstract WindowFunction getWindowFunction();

}
