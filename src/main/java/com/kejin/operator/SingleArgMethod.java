package com.kejin.operator;

import com.kejin.enums.CompileException;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.function.Function;

public abstract class SingleArgMethod extends MethodOperators {

    @Override
    public final void check(List<Var> argList)  {
        if (argList.size() != 1) {
            throw new CompileException(symbol() + "函数只接受一个参数");
        }
        checkArg(argList.get(0));
    }

    protected abstract void checkArg(Var arg) ;


    @Override
    protected final Function<List<Value>, Value> calculateFunction() {
        return s -> calculateSingle().apply(s.get(0));
    }

    protected abstract Function<Value, Value> calculateSingle();


}
