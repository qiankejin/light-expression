package com.kejin.expression.param;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapParam implements ParamCollection {

    private final Map<String, Value<?>> paramMap;

    public MapParam(Map<String, Object> param) {
        paramMap = new HashMap<>(param.size());
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            paramMap.put(entry.getKey(), Value.of(entry.getValue()));
        }
    }

    public MapParam() {
        paramMap = new HashMap<>();
    }

    @Override
    public Value<?> get(String name) {
        Value<?> value = paramMap.get(name);
        if (value == null) {
            throw new ExecuteException(ErrorCode.ARG_MISS, name);
        }
        return value;
    }


    @Override
    public void put(String key, Object value) {
        paramMap.put(key, Value.of(value));
    }
}
