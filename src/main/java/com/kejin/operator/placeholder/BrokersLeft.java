package com.kejin.operator.placeholder;


import com.kejin.enums.Lexical;
import com.kejin.operator.PlaceholderOperator;

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
