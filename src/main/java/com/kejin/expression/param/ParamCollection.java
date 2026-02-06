package com.kejin.expression.param;

import com.kejin.expression.value.Value;

public interface ParamCollection {

    Value<?> get(String name);

    void put(String name, Object value);
}
