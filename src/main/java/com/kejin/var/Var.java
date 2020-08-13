package com.kejin.var;


import com.kejin.Node;
import com.kejin.enums.Lexical;
import com.kejin.enums.ValueType;
import com.kejin.value.Value;

import java.util.Map;
import java.util.Set;

public interface Var extends Node {

    @Override
    default Lexical lexical() {
        return Lexical.ARG;
    }

    ValueType valueType();

    Value fill(Map<String, Value> varMap);

    void usedArg(Set<String> args);

}
