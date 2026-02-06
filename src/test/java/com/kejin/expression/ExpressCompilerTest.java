package com.kejin.expression;

import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressCompilerTest {

    @Test
    public void testComplexExpressionEvaluation() throws Exception {
        // 测试复杂的表达式解析和执行
        String expression = "IF(1>0, 100, 200)";
        Var compiled = ExpressCompiler.compile(expression);
        Value<?> result = compiled.execute(new MapParam());
        assertEquals(100L, result.toLong());
    }
}