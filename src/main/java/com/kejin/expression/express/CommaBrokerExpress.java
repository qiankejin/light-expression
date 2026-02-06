package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CommaBrokerExpress extends BrokersExpress {
    private final CommaExpress commaExpress;

    public CommaBrokerExpress(CommaExpress commaExpress) {
        this.commaExpress = commaExpress;
    }

    @Override
    public String show() {
        return commaExpress.toString();
    }

    @Override
    public List<Value<?>> fillArgList(ParamCollection param) {
        return commaExpress.fillArgList(param);
    }

    @Override
    public ValueType valueType() {
        return commaExpress.valueType();
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        return commaExpress.execute(param);
    }

    @Override
    public void usedArg(Set<String> args) {
        commaExpress.usedArg(args);
    }

    public List<Var> getArgList() {
        return commaExpress.getArgList();
    }
}
