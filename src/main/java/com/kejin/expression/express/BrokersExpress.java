package com.kejin.expression.express;


import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class BrokersExpress implements Express {


    protected BrokersExpress() {
    }

    @Override
    public String toString() {
        return "(" + show() + ")";
    }

    public abstract String show();

    public abstract List<Value<?>> fillArgList(ParamCollection param);

    public abstract List<Var> getArgList();

    public static BrokersExpress of(Var var) {
        if (var == null) {
            return new EmptyBrokerExpress();
        }
        if (var instanceof CommaExpress) {
            return new CommaBrokerExpress((CommaExpress) var);
        }
        return new SingleBrokerExpress(var);
    }

}
