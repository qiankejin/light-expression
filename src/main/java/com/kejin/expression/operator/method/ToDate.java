package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.DateValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ToDate extends MultiArgMethod {
    @Override
    public void checkArg(List<Var> argList) {
        if (argList.size() > 2) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化入参不能超过两个");
        }
        if (!argList.isEmpty()) {
            if (argList.get(0).valueType() != ValueType.TEXT) {
                throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化入参必须是字符串");
            }
            if (argList.size() == 2) {
                if (argList.get(1).valueType() != ValueType.TEXT) {
                    throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化入参必须是字符串");
                }
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.DATE;
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        int argSize = args.size();
        if (argSize == 0) {
            return new DateValue();
        }
        Value<?> value = args.get(0).execute(param);
        String format = DEFAULT_FORMAT;
        if (argSize == 2) {
            format = args.get(1).execute(param).toString();
        }
        String text = value.toString();
        try {
            return new DateValue(new SimpleDateFormat(format).parse(text));
        } catch (Exception e) {
            throw new ExecuteException(ErrorCode.FORMAT_ERROR, "日期格式化错误：" + text, e);
        }
    }

    @Override
    public String symbol() {
        return "TO_DATE";
    }
}
