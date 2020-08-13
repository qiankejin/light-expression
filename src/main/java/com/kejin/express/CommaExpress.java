package com.kejin.express;

import com.kejin.enums.Lexical;
import com.kejin.enums.ValueType;
import com.kejin.value.Value;
import com.kejin.var.Var;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CommaExpress implements Var {

    private List<Var> argList;

    public CommaExpress(List<Var> argList) {
        this.argList = argList;
    }

    @Override
    public Lexical lexical() {
        return Lexical.ARG_LIST;
    }

    @Override
    public ValueType valueType() {
        return ValueType.NONE;
    }

    @Override
    public Value fill(Map<String, Value> varMap) {
        return argList.get(0).fill(varMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Var var : argList) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(var.toString());
        }
        return sb.toString();
    }


    public List<Value> fillArgList(Map<String, Value> varMap) {
        return argList.stream().map(s -> s.fill(varMap)).collect(Collectors.toList());
    }

    public List<Var> getArgList() {
        return argList;
    }

    public void usedArg(Set<String> args) {
        argList.forEach(s -> s.usedArg(args));
    }
}
