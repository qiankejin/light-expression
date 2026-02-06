package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.DoubleValue;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.math.BigDecimal;

/**
 * @author 钱克金
 */
public class ToNumber extends SingleArgMethod {

    @Override
    protected void checkArg(Var arg) throws ExpressionException {
        if (arg.valueType() != ValueType.TEXT) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "转整数入参必须是字符串");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    protected Value<?> methodCalculate(Var express, ParamCollection param) {
        String str = express.execute(param).toString();
        try {
            if (str.indexOf('.') != -1) {
                return DoubleValue.of(new BigDecimal(str));
            } else if (str.length() <= 10) {
                return LongValue.of(Integer.parseInt(str));
            } else {
                return LongValue.of(Long.parseLong(str));
            }
        } catch (Exception e) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, str + "无法转换为数字");
        }
    }

    @Override
    public String symbol() {
        return "TO_NUM";
    }
}
