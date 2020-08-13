package com.kejin.var;


import com.kejin.enums.ValueType;

public interface TextVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.TEXT;
    }
}
