package com.kejin.operator.calculate;

import com.kejin.enums.CompileException;
import com.kejin.operator.CalculateOperator;
import com.kejin.value.Value;
import com.kejin.var.Arg;
import com.kejin.var.Const;
import com.kejin.var.Var;

import java.util.Map;
import java.util.function.BiFunction;

import static com.kejin.enums.OperatorsPriority.LEVEL_12;

public class Case extends CalculateOperator {

    private Arg switchArg;

    @Override
    public Value calculate(Var left, Var right, Map<String, Value> varMap) {
        Value switchValue = switchArg.fill(varMap);
        if (switchValue.eq(left.fill(varMap))) {
            return right.fill(varMap);
        }
        return null;
    }

    public void setSwitchArg(Arg switchArg) {
        this.switchArg = switchArg;
    }

    @Override
    public void check(Var left, Var right)  {
        if (!(left instanceof Const)) {
            throw new CompileException("case左边必须是常量");
        }
    }

    @Override
    protected BiFunction<Value, Value, Value> calculateFunction() {
        return null;
    }

    @Override
    public int priority() {
        return LEVEL_12;
    }

    @Override
    public String symbol() {
        return ":";
    }
}
