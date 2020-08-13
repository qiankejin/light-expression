package com.kejin.operator;

import com.kejin.enums.CompileException;
import com.kejin.enums.Lexical;
import com.kejin.enums.ValueType;
import com.kejin.express.BrokersExpress;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.kejin.enums.OperatorsPriority.LEVEL_1;

public abstract class MethodOperators extends Operators {

    @Override
    public int priority() {
        return LEVEL_1;
    }

    public abstract void check(List<Var> argList) throws CompileException;

    public abstract ValueType methodReturnType(BrokersExpress brokers);

    protected abstract Function<List<Value>, Value> calculateFunction();

    public Value calculate(BrokersExpress node, Map<String, Value> varMap) {
        List<Value> values = node.fillArgList(varMap);
        for (Value value : values) {
            if (!value.isSuccess()) {
                return value;
            }
        }
        return calculateFunction().apply(values);
    }

    @Override
    public Lexical lexical() {
        return Lexical.METHOD;
    }
}
