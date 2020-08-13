package com.kejin.operator.placeholder;


import com.kejin.enums.Lexical;
import com.kejin.operator.PlaceholderOperator;

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
