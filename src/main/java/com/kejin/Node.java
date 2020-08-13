package com.kejin;

import com.kejin.enums.Lexical;

public interface Node {

    Lexical lexical();

    @Override
    String toString();

}
