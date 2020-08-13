package com.kejin.express;


import com.kejin.enums.ValueType;
import com.kejin.util.StringUtil;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BrokersExpress implements Express {

    private List<Var> argList;


    public BrokersExpress(Var argList) {
        if (argList instanceof CommaExpress) {
            this.argList = ((CommaExpress) argList).getArgList();
        } else {
            this.argList = Collections.singletonList(argList);
        }
    }

    @Override
    public void usedArg(Set<String> args) {
        argList.forEach(s -> s.usedArg(args));
    }

    @Override
    public String toString() {
        return "(" + StringUtil.join(",", argList) + ")";

    }

    public List<Var> getArgList() {
        return argList;
    }

    @Override
    public ValueType valueType() {
        return argList.get(0).valueType();
    }

    @Override
    public Value fill(Map<String, Value> varMap) {
        return argList.get(0).fill(varMap);
    }

    public List<Value> fillArgList(Map<String, Value> varMap) {
        return argList.stream().map(s -> s.fill(varMap)).collect(Collectors.toList());
    }

}
