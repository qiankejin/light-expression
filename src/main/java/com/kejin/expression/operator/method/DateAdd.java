package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.IntegerEnum;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.EnumUtil;
import com.kejin.expression.value.DateValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 钱克金
 */
public class DateAdd extends MultiArgMethod {
    @Override
    public void checkArg(List<Var> argList) throws ExecuteException {
        if (argList.size() != 3) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期计算函数入参必须是三个");
        }
        if (argList.get(0).valueType() != ValueType.NUMBER) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期结束函数第一个入参入参必须是数字");
        }
        if (argList.get(1).valueType() != ValueType.NUMBER) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期结束函数第一个入参入参必须是数字");
        }
        if (argList.get(2).valueType() != ValueType.DATE) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期结束函数第二个入参入参必须是日期");
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.DATE;
    }

    private enum AddType implements IntegerEnum {
        YEAR(1, "增加年"),
        MONTH(2, "增加月"),
        DAY(3, "增加天"),
        HOUR(4, "增加时"),
        MINUTES(5, "增加分"),
        SECONDS(6, "增加秒"),
        ;
        private final int code;
        private final String desc;

        AddType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getDesc() {
            return desc;
        }
    }

    @Override
    public DateValue calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        int type = args.get(0).execute(param).toInt();
        int add = args.get(1).execute(param).toInt();
        Date date = args.get(2).execute(param).toDate();
        AddType addType = EnumUtil.getByCode(type, AddType.class);
        if (addType == null) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期计算类型不合法：" + args.get(0).toString());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (addType) {
            case YEAR:
                calendar.add(Calendar.YEAR, add);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, add);
                break;
            case DAY:
                calendar.add(Calendar.DAY_OF_MONTH, add);
                break;
            case HOUR:
                calendar.add(Calendar.HOUR_OF_DAY, add);
                break;
            case MINUTES:
                calendar.add(Calendar.MINUTE, add);
                break;
            case SECONDS:
                calendar.add(Calendar.SECOND, add);
                break;
            default:
                throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期计算类型不合法：" + args.get(0).toString());
        }

        return Value.of(calendar.getTime());
    }


    @Override
    public String symbol() {
        return "DATE_ADD";
    }
}
