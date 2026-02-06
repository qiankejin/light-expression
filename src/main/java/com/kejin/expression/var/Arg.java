package com.kejin.expression.var;


import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;

import java.util.Set;

public abstract class Arg implements Var {
    protected final String code;

    @Override
    public final void usedArg(Set<String> args) {
        args.add(code);
    }

    public Arg(String code) {
        this.code = code;
    }

    @Override
    public final Value<?> execute(ParamCollection param) {

        Value<?> value = param.get(code);
        if (value == null) {
            throw new ExecuteException(ErrorCode.ARG_MISS, "变量" + code + "缺失");
        }
        ValueType valueType = valueType();
        if (!value.type().accept(valueType)) {
            throw new ExecuteException(ErrorCode.FORMAT_ERROR, "变量" + code + "不是" + valueType);
        }
        return value;
    }

    @Override
    public final String toString() {
        return code;
    }
}
