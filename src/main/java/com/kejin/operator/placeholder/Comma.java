package com.kejin.operator.placeholder;

import com.kejin.enums.Lexical;
import com.kejin.operator.PlaceholderOperator;

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
