package com.kejin.expression.express;

import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CommaExpress implements Var {

    private final List<Var> argList;

    public CommaExpress(List<Var> argList) {
        this.argList = argList;
    }

    @Override
    public Lexical lexical() {
        return Lexical.ARG_LIST;
    }

    @Override
    public ValueType valueType() {
        return argList.get(argList.size() - 1).valueType();
    }

    @Override
    public Value<?> execute(ParamCollection param) {
        Value<?> result = null;
        for (Var var : argList) {
            result = var.execute(param);
        }
        return result;
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


    public List<Value<?>> fillArgList(ParamCollection param) {
        return argList.stream().map(s -> s.execute(param)).collect(Collectors.toList());
    }

    public void usedArg(Set<String> args) {
        argList.forEach(s -> s.usedArg(args));
    }
}
