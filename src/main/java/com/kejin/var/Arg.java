package com.kejin.var;


import com.kejin.enums.ErrorCode;
import com.kejin.enums.ExTreeCache;
import com.kejin.enums.ValueType;
import com.kejin.value.Value;

import java.util.Map;
import java.util.Set;

public abstract class Arg implements Var {
    protected String code;
    protected static final ExTreeCache<String, Arg> compileCache = new ExTreeCache<>(1024);

    @Override
    public final void usedArg(Set<String> args) {
        args.add(code);
    }


    @Override
    public final Value fill(Map<String, Value> varMap) {
        Value value = varMap.get(code);
        if (value == null) {
            return Value.error(ErrorCode.ARG_MISS, "变量" + code + "缺失");
        } else {
            ValueType valueType = valueType();
            if (value.type() != valueType) {
                return Value.error(ErrorCode.FORMAT_ERROR, "变量" + code + "不是" + valueType);
            }
            return value;
        }
    }

    @Override
    public final String toString() {
        return code;
    }
}
