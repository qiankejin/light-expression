package com.kejin.expression.operator.placeholder;


import com.kejin.expression.enums.Lexical;
import com.kejin.expression.operator.PlaceholderOperator;

public class Comma extends PlaceholderOperator {

    @Override
    public Lexical lexical() {
        return Lexical.ARG_LIST;
    }

    @Override
    public String symbol() {
        return ",";
    }
}
