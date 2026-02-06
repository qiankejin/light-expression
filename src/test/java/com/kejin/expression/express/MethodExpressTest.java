package com.kejin.expression.express;

import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.BooleanConst;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import com.kejin.expression.value.Value;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MethodExpressTest {

    @Test
    public void testMethodExpressWithIF() {
        // 测试IF方法表达式的功能
        MethodOperators ifOp = new com.kejin.expression.operator.method.IF();
        Var condition = BooleanConst.TRUE_CONST;
        Var trueValue = NumberConst.of("100");
        Var falseValue = NumberConst.of("200");
        
        BrokersExpress args = BrokersExpress.of(new CommaExpress(Arrays.asList(condition, trueValue, falseValue)));
        MethodExpress ifExpr = new MethodExpress(ifOp, args);
        
        Value<?> result = ifExpr.execute(new MapParam());
        assertEquals(100L, result.toLong());
        
        // 测试条件为假的情况
        Var falseCondition = BooleanConst.FALSE_CONST;
        BrokersExpress args2 = BrokersExpress.of(new CommaExpress(Arrays.asList(falseCondition, trueValue, falseValue)));
        MethodExpress ifExpr2 = new MethodExpress(ifOp, args2);
        
        Value<?> result2 = ifExpr2.execute(new MapParam());
        assertEquals(200L, result2.toLong());
    }
}