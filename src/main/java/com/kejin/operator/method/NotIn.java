package com.kejin.operator.method;

import com.kejin.enums.CompileException;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.operator.MethodOperators;
import com.kejin.value.BooleanValue;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.function.Function;

public class NotIn extends MethodOperators {

    @Override
    public String symbol() {
        return "NOTIN";
    }

    @Override
    public void check(List<Var> argList)  {
        if (argList.size() < 2) {
            throw new CompileException("NOTIN函数参数个数必须大于2");
        }
        ValueType type = null;
        for (Var express : argList) {
            if (type == null) {
                type = express.valueType();
            } else if (type != express.valueType()) {
                throw new CompileException("NOTIN函数参数必须是相同类型");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return ValueType.BOOLEAN;
    }

    @Override
    protected Function<List<Value>, Value> calculateFunction() {
        return v -> {
            Value value = v.get(0);
            for (int i = 1; i < v.size(); i++) {
                if (value.eq(v.get(i))) {
                    return BooleanValue.FALSE;
                }
            }
            return BooleanValue.TRUE;
        };
    }
}
