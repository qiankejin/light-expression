package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SingleBrokerExpress extends BrokersExpress {

    private final Var single;

    public SingleBrokerExpress(Var var) {
        this.single = var;
    }

    public Var getSingleArg() {
        return single;
    }

    @Override
    public List<Var> getArgList() {
        return Collections.singletonList(single);
    }

    @Override
    public String show() {
        return single.toString();
    }

    @Override
    public List<Value<?>> fillArgList(ParamCollection param) {
        return Collections.singletonList(single.execute(param));
    }

    @Override
    public ValueType valueType() {
        return single.valueType();
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        return single.execute(param);
    }

    @Override
    public void usedArg(Set<String> args) {
        single.usedArg(args);
    }
}
