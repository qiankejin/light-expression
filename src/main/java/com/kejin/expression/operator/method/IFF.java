package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CaseExpress;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

/**
 * IFF函数类，用于条件判断操作
 * IFF函数接受多个CaseExpress参数，类似于if-else-if的链式条件判断
 * 每个CaseExpress包含一个布尔条件和对应的返回值，函数会按顺序执行直到找到第一个条件为真的CaseExpress
 *
 * @author 钱克金
 */
public class IFF extends MultiArgMethod {

    /**
     * 检查IFF函数参数的有效性
     * IFF函数用于条件判断，类似其他语言中的三元操作符或if-else-if链
     *
     * @param argList 参数列表，每个参数必须是CaseExpress类型
     * @throws ExpressionException 当参数不符合IFF函数要求时抛出
     */
    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        // 验证参数数量至少为2个
        if (argList.size() < 2) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "IFF函数入参最少2个");
        }
        ValueType returnType = null;
        // 遍历所有参数，验证每个参数都是CaseExpress类型且满足条件
        for (Var a : argList) {
            // 验证参数必须是CaseExpress实例
            if ((!(a instanceof CaseExpress))) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "IFF函数参数必须是case表达式");
            }
            // 验证CaseExpress的条件部分必须是布尔类型
            if (((CaseExpress) a).getWhenVar().valueType() != ValueType.BOOLEAN) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "IFF函数的case表达式左边必须是布尔类型");
            }
            // 验证所有CaseExpression的返回值类型一致
            if (returnType == null) {
                returnType = a.valueType();
            } else if (!a.valueType().accept(returnType)) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "IFF函数的case表达式返回类型必须相同");

            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        // 返回第一个参数的类型作为整个IFF表达式的返回类型
        return brokers.getArgList().get(0).valueType();
    }


    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> argList = node.getArgList();
        // 按顺序遍历所有CaseExpress，找到第一个条件为真的Case并返回其值
        for (int i = 0; i < argList.size(); i++) {
            CaseExpress ca = ((CaseExpress) argList.get(i));
            Value<?> fill = ca.caseFill(null, param);
            if (fill != null) {
                return fill;
            }
        }
        // 如果没有找到符合条件的CaseExpress，抛出异常
        throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "IFF无法选中合适条件");
    }

    @Override
    public String symbol() {
        // 返回IFF函数的符号名称
        return "IFF";
    }
}
