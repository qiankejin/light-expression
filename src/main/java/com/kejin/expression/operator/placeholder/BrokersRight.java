package com.kejin.expression.operator.placeholder;


import com.kejin.expression.enums.Lexical;
import com.kejin.expression.operator.PlaceholderOperator;

public class BrokersRight extends PlaceholderOperator {
    @Override
    public Lexical lexical() {
        return Lexical.BLOCKERS_RIGHT;
    }

    @Override
    public String symbol() {
        return ")";
    }


}
