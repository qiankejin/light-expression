package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.operator.SingleArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

/**
 * @author 钱克金
 */
public class Seq extends SingleArgMethod {

    private static SequenceGenerator sequenceGenerator;

    public static void setSequenceGenerator(SequenceGenerator sequenceGenerator) {
        Seq.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.NUMBER;
    }

    @Override
    public String symbol() {
        return "SEQ";
    }

    @Override
    protected void checkArg(Var arg) throws ExpressionException {
        if (arg.valueType() != ValueType.TEXT) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "序列号生成入参必须是字符串");
        }
    }
    @Override
    protected LongValue methodCalculate(Var arg, ParamCollection param) {
        if (sequenceGenerator == null) {
            throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "序列号生成器的具体实现没有注入");
        }
        String seqKey = arg.execute(param).toString();
        long seq = sequenceGenerator.next(seqKey);
        return Value.of(seq);
    }
}
