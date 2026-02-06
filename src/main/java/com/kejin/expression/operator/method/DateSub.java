package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.IntegerEnum;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.util.EnumUtil;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Date;
import java.util.List;


public class DateSub extends MultiArgMethod {

    @Override
    public void checkArg(List<Var> argList) throws ExecuteException {
        if (argList.size() != 2 && argList.size() != 3) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期相减函数入参必须为二个或三个");
        }
        if (argList.get(0).valueType() != ValueType.DATE || argList.get(1).valueType() != ValueType.DATE) {
            throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期相减函数第一个和第二个入参必须是日期对象");
        }
        if (argList.size() == 3) {
            if (argList.get(2).valueType() != ValueType.NUMBER) {
                throw new ExecuteException(ErrorCode.GRAMMAR_ERROR, "日期相减函数第三个入参必须是数字");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    private enum SubType implements IntegerEnum {

        SECOND(1, "秒"),
        MINUTE(2, "分"),
        HOUR(3, "小时"),
        DAY(4, "天"),
        ;
        private final int code;
        private final String desc;

        SubType(int code, String desc) {
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
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> args = node.getArgList();
        int size = args.size();
        Date date1 = args.get(0).execute(param).toDate();
        Date date2 = args.get(1).execute(param).toDate();
        long diffInMillis = date1.getTime() - date2.getTime();
        if (size != 3) {
            return new LongValue(diffInMillis / (1000 * 60 * 60 * 24));
        } else {
            int type = args.get(2).execute(param).toInt();
            SubType subType = EnumUtil.getByCode(type, SubType.class);
            if (subType == null) {
                throw new ExecuteException(ErrorCode.FORMAT_ERROR, "日期相减类型无法识别");
            }
            switch (subType) {
                case SECOND:
                    return new LongValue(diffInMillis / 1000);
                case MINUTE:
                    return new LongValue(diffInMillis / (1000 * 60));
                case HOUR:
                    return new LongValue(diffInMillis / (1000 * 60 * 60));
                case DAY:
                    return new LongValue(diffInMillis / (1000 * 60 * 60 * 24));
                default:
                    throw new ExecuteException(ErrorCode.FORMAT_ERROR, "日期相减类型无法识别");
            }
        }
    }

    @Override
    public String symbol() {
        return "DATE_SUB";
    }
}