package com.kejin.expression.operator;


import com.kejin.expression.Node;

public abstract class Operators implements Node {

    public abstract int priority();

    public abstract String symbol();

    @Override
    public final String toString() {
        return symbol();
    }
}
