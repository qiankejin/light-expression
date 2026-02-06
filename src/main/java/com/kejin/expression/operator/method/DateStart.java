package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.IntegerEnum;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.operator.TwoArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.EnumUtil;
import com.kejin.expression.value.DateValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 钱克金
 */
public class DateStart extends TwoArgMethod {
    @Override
    public void checkArg(Var argOne, Var argTwo) throws ExecuteException {
        if (argOne.valueType() != ValueType.NUMBER) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期开始函数第一个入参入参必须是数字");
        }
        if (argTwo.valueType() != ValueType.DATE) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期开始函数第二个入参入参必须是日期");
        }
    }

    private enum StartType implements IntegerEnum {

        YEAR(1, "年开头"),
        MONTH(2, "月开头"),
        DAY(3, "天开头"),
        WEEK_1(4, "星期日开头"),
        WEEK_2(5, "星期一开头"),
        ;
        private final int code;
        private final String desc;

        StartType(int code, String desc) {
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
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.DATE;
    }


    @Override
    protected DateValue methodCalculate(Var argOne, Var argTwo, ParamCollection param) {
        int type = argOne.execute(param).toInt();
        Date date = argTwo.execute(param).toDate();
        StartType startType = EnumUtil.getByCode(type, StartType.class);
        if (startType == null) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期开始类型不合法：" + argOne);
        }
        switch (startType) {
            case DAY:
                return Value.of(startOfDay(date));
            case MONTH:
                return Value.of(startOfMonth(date));
            case YEAR:
                return Value.of(startOfYear(date));
            case WEEK_1:
                return Value.of(startOfWeekSunday(date));
            case WEEK_2:
                return Value.of(startOfWeekMonday(date));
            default:
                throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期开始类型不合法：" + argOne);
        }
    }

    private static Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date startOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date startOfWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date startOfWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date startOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    @Override
    public String symbol() {
        return "DATE_START";
    }
}
