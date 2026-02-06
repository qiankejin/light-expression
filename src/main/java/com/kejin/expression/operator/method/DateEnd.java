package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.IntegerEnum;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.TwoArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.EnumUtil;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 钱克金
 */
public class DateEnd extends TwoArgMethod {
    @Override
    public void checkArg(Var argOne,Var argTwo) throws ExecuteException {
        if (argOne.valueType() != ValueType.NUMBER) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期结束函数第一个入参入参必须是数字");
        }
        if (argTwo.valueType() != ValueType.DATE) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期结束函数第二个入参入参必须是日期");
        }
    }

    private enum EndType implements IntegerEnum {
        YEAR(1, "年结束"),
        MONTH(2, "月结束"),
        DAY(3, "天结束"),
        WEEK_1(4, "星期日结束"),
        WEEK_2(5, "星期一结束"),
        ;
        private final int code;
        private final String desc;

        EndType(int code, String desc) {
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
    protected Value<?> methodCalculate(Var argOne, Var argTwo, ParamCollection param) {
        int type = argOne.execute(param).toInt();
        Date date = argTwo.execute(param).toDate();
        EndType endType = EnumUtil.getByCode(type, EndType.class);
        if (endType == null) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期开始类型不合法：" + argOne);
        }
        switch (endType) {
            case DAY:
                return Value.of(endOfDay(date));
            case MONTH:
                return Value.of(endOfMonth(date));
            case YEAR:
                return Value.of(endOfYear(date));
            case WEEK_1:
                return Value.of(endOfWeekSunday(date));
            case WEEK_2:
                return Value.of(endOfWeekMonday(date));
            default:
                throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "日期开始类型不合法：" + argTwo);
        }
    }

    private static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private static Date endOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private static Date endOfWeekSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.DAY_OF_WEEK, 6); // 从周日算起，加上6天就是周六
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private static Date endOfWeekMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DAY_OF_WEEK, 6); // 从周一算起，加上6天就是周日
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private static Date endOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    @Override
    public String symbol() {
        return "DATE_END";
    }
}
