package com.kejin.expression.operator.method;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CaseExpress;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.MultiArgMethod;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;

import java.util.List;

public class Switch extends MultiArgMethod {

    @Override
    public void checkArg(List<Var> argList) throws ExpressionException {
        Var switchArg = argList.get(0);

        ValueType returnType = null;
        for (int i = 1; i < argList.size(); i++) {
            Var a = argList.get(i);
            if ((!(a instanceof CaseExpress))) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "switch函数其他参数必须是case表达式");
            }
            CaseExpress c = (CaseExpress) a;
            if (!c.isDefault()) {
                if (!switchArg.valueType().accept(c.getWhenVar().valueType())) {
                    throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "switch函数的case表达式类型必须相同");
                }
            }
            if (returnType == null) {
                returnType = a.valueType();
            } else if (!a.valueType().accept(returnType)) {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "switch函数的case表达式返回类型必须相同");
            }
        }
    }

    @Override
    public ValueType methodReturnType(BrokersExpress brokers) {
        return brokers.getArgList().get(0).valueType();
    }


    @Override
    public Value<?> calculate(BrokersExpress node, ParamCollection param) {
        List<Var> argList = node.getArgList();
        Var switchVar = argList.get(0);
        Value<?> switchValue = switchVar.execute(param);
        for (int i = 1; i < argList.size(); i++) {
            CaseExpress ca = ((CaseExpress) argList.get(i));
            Value<?> fill = ca.caseFill(switchValue, param);
            if (fill != null) {
                return fill;
            }
        }
        throw new ExecuteException(ErrorCode.CALCULATE_ERROR, "switch无法选中合适条件");
    }

    @Override
    public String symbol() {
        return "SWITCH";
    }
}
