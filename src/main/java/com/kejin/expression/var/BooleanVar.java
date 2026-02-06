package com.kejin.expression.var;


import com.kejin.expression.enums.ValueType;

public interface BooleanVar extends Var {
    @Override
    default ValueType valueType() {
        return ValueType.BOOLEAN;
    }

}
