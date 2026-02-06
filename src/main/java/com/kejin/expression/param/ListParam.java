package com.kejin.expression.param;

import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.value.Value;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kejin.expression.enums.ErrorCode.GRAMMAR_ERROR;

@Getter
public class ListParam implements ParamCollection {
    private final List<MapParam> listData;

    public ListParam(List<Map<String, Object>> dataList) {
        this.listData = dataList.stream().map(MapParam::new).collect(Collectors.toList());
    }

    public ListParam() {
        this.listData = new ArrayList<>();
    }

    @Override
    public Value<?> get(String name) {
        throw new ExecuteException(GRAMMAR_ERROR, "列表数据不支持获取属性操作");
    }

    @Override
    public void put(String name, Object value) {
        throw new ExecuteException(GRAMMAR_ERROR, "列表数据不支持设值操作");
    }

}
