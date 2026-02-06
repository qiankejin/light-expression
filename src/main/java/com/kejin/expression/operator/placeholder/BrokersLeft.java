package com.kejin.expression.operator.placeholder;


import com.kejin.expression.enums.Lexical;
import com.kejin.expression.operator.PlaceholderOperator;

public class BrokersLeft extends PlaceholderOperator {

    @Override
    public String symbol() {
        return "(";
    }

    @Override
    public Lexical lexical() {
        return Lexical.BLOCKERS_LEFT;
    }
}
