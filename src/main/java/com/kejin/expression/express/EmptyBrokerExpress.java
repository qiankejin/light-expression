package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EmptyBrokerExpress extends BrokersExpress {

    @Override
    public String show() {
        return "";
    }

    @Override
    public List<Value<?>> fillArgList(ParamCollection param) {
        return Collections.emptyList();
    }

    @Override
    public ValueType valueType() {
        return null;
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        return null;
    }

    @Override
    public void usedArg(Set<String> args) {

    }

    @Override
    public List<Var> getArgList() {
        return Collections.emptyList();
    }
}
