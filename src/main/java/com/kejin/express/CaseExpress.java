package com.kejin.express;

import com.kejin.enums.ValueType;
import com.kejin.value.Value;
import com.kejin.var.Arg;
import com.kejin.var.Const;
import com.kejin.var.Var;

import java.util.Map;
import java.util.Set;

public class CaseExpress implements Express {
    private Arg switchArg;
    private final Const whenVar;
    private final Var thenVar;


    public CaseExpress(Const whenVar, Var thenVar) {
        this.whenVar = whenVar;
        this.thenVar = thenVar;
    }

    public Const getWhenVar() {
        return whenVar;
    }

    public Var getThenVar() {
        return thenVar;
    }

    public void setSwitchArg(Arg switchArg) {
        this.switchArg = switchArg;
    }

    @Override
    public ValueType valueType() {
        return thenVar.valueType();
    }

    @Override
    public Value fill(Map<String, Value> varMap) {
        Value switchValue = switchArg.fill(varMap);
        Value whenValue = whenVar.fill(varMap);
        if (switchValue.eq(whenValue)) {
            return thenVar.fill(varMap);
        }
        return null;
    }

    @Override
    public String toString() {
        return whenVar+":"+thenVar;
    }

    @Override
    public void usedArg(Set<String> args) {
        whenVar.usedArg(args);
        thenVar.usedArg(args);
    }

}
