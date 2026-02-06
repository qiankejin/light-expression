package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.CollectionUtils;
import com.kejin.expression.value.TextValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 钱克金
 */
public class DateFormat extends MultiArgMethod {
    @Override
    public void checkArg(List<Var> argList) throws ExecuteException {
        if (argList.size() > 2) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化入参不能超过两个");
        }
        if (CollectionUtils.isEmpty(argList)) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化必须要有入参");
        }
        if (argList.get(0).valueType() != ValueType.DATE) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化第一个入参必须是日期类型");
        }
        if (argList.size() == 2) {
            if (argList.get(1).valueType() != ValueType.TEXT) {
                throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期格式化第二个入参必须是字符串");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.TEXT;
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public TextValue calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        int argSize = args.size();
        Value<?> value = args.get(0).execute(param);
        String format = DEFAULT_FORMAT;
        if (argSize == 2) {
            format = args.get(1).execute(param).toString();
        }
        Date date = value.toDate();
        return new TextValue(new SimpleDateFormat(format).format(date));
    }


    @Override
    public String symbol() {
        return "DATE_FORMAT";
    }
}
