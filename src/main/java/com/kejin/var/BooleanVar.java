package com.kejin.var;


import com.kejin.enums.ValueType;

public interface BooleanVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.BOOLEAN;
    }

}
