package com.kejin.var;

import com.kejin.enums.ExTreeCache;
import com.kejin.value.Value;

import java.util.Set;

public abstract class Const implements Var {
    protected Value value;
    protected static final ExTreeCache<String, Const> compileCache = new ExTreeCache<>(1024);


    @Override
    public final void usedArg(Set<String> args) {

    }

}
