package com.kejin.expression.var;

import com.kejin.expression.value.Value;

import java.util.Set;

public abstract class Const implements Var {
    protected Value<?> value;

    @Override
    public final void usedArg(Set<String> args) {

    }

    public Value<?> getValue() {
        return value;
    }
}
