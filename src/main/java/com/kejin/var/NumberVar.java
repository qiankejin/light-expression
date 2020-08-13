package com.kejin.var;


import com.kejin.enums.ValueType;

public interface NumberVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.NUMBER;
    }
}
