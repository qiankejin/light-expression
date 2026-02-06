package com.kejin.expression.operator;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.express.BrokersExpress;
import com.kejin.expression.express.CommaExpress;
import com.kejin.expression.var.NumberArg;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import com.kejin.expression.operator.calculate.Eval;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.Value;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import com.kejin.expression.errors.ExpressionException;

public class EvalOperatorTest {

    @Test
    public void testEvalOperator() {
        // 测试Eval赋值操作符
        Eval eval = new Eval();
        assertEquals("=", eval.symbol());
        assertEquals(12, eval.priority()); // LEVEL_12

        // 测试返回类型
        Var left = NumberArg.of("x");
        Var right = NumberConst.of("10");
        assertEquals(ValueType.NUMBER, eval.returnType(left, right));

        // 测试检查功能
        eval.check(left, right); // 应该正常通过

        // 测试左侧非Arg的情况
        try {
            eval.check(NumberConst.of("5"), right);
            fail("应该抛出异常，因为赋值表达式左边不是变量");
        } catch (ExpressionException e) {
            assertEquals(80000003, e.getCode()); // GRAMMAR_ERROR
        }

        // 测试计算功能
        MapParam params = new MapParam();
        Value<?> result = eval.calculate(left, right, params);
        assertEquals(10L, result.toLong());
        assertEquals(10L, params.get("x").toLong()); // 验证赋值成功
    }
}