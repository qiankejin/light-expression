package com.kejin.expression.operator.single;

import com.kejin.TestUtil;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.SingleOperator;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.BooleanArg;
import com.kejin.expression.var.BooleanConst;
import com.kejin.expression.var.NumberArg;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.TextArg;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotTest {

    @Test
    public void testNotOperatorBasicFunctionality() {
        Not not = new Not();
        
        // 测试基本属性
        assertEquals("!", not.symbol());
        assertEquals(1, not.priority()); // LEVEL_1 (单目运算符优先级)
        
        // 测试返回类型
        Var operand = BooleanConst.TRUE_CONST;
        assertEquals(ValueType.BOOLEAN, not.returnType(operand));
    }

    @Test
    public void testNotOperatorCalculation() {
        Not not = new Not();
        MapParam param = new MapParam();
        
        // 测试 !true = false
        Value<?> result1 = not.calculate(BooleanConst.TRUE_CONST, param);
        assertFalse(result1.toBoolean());
        
        // 测试 !false = true
        Value<?> result2 = not.calculate(BooleanConst.FALSE_CONST, param);
        assertTrue(result2.toBoolean());
    }

    @Test
    public void testNotWithBooleanArguments() {
        Not not = new Not();
        MapParam param = new MapParam();
        
        // 测试使用布尔参数变量
        param.put("flag", Value.of(true));
        Value<?> result1 = not.calculate(BooleanArg.of("flag"), param);
        assertFalse(result1.toBoolean());
        
        param.put("flag", Value.of(false));
        Value<?> result2 = not.calculate(BooleanArg.of("flag"), param);
        assertTrue(result2.toBoolean());
    }



    @Test
    public void testNotOperatorPrecedence() {
        // 测试运算符优先级
        // ! 的优先级是 1，是最高的单目运算符优先级
        Not not = new Not();
        assertEquals(1, not.priority());
    }

    @Test
    public void testNotWithExpression() {
        // 测试实际表达式求值
        TestUtil.execute("!true", false);
        TestUtil.execute("!false", true);
        TestUtil.execute("!false||true", true);
        TestUtil.execute("!(true&&false)", true);
    }

    @Test
    public void testNotOperatorInheritance() {
        Not not = new Not();
        assertTrue(not instanceof SingleOperator);
    }

    @Test
    public void testNotWithComplexExpressions() {
        Not not = new Not();
        MapParam param = new MapParam();
        
        // 测试复杂表达式: !(5 > 3) = !(true) = false
        param.put("a", Value.of(5L));
        param.put("b", Value.of(3L));
        // 注意：这里需要模拟比较运算符，实际测试中会通过表达式引擎处理
        
        // 测试嵌套否定: !!true = true
        param.put("flag", Value.of(true));
        Value<?> intermediateResult = not.calculate(BooleanArg.of("flag"), param);
        // 手动创建第二次否定的参数
        Value<?> finalResult = Value.of(!intermediateResult.toBoolean());
        assertTrue(finalResult.toBoolean());
    }

    @Test
    public void testNotOperatorSymbol() {
        Not not = new Not();
        assertEquals("!", not.symbol());
    }

    @Test
    public void testNotWithNullValues() {
        Not not = new Not();
        MapParam param = new MapParam();
        
        // 测试null值处理
        try {
            param.put("nullValue", Value.of((Object)null));
            not.calculate(TextArg.of("nullValue"), param);
            fail("Should throw exception for null conversion");
        } catch (RuntimeException e) {
            // Expected - null cannot be converted to boolean
        }
    }

    @Test
    public void testNotReturnTypeConsistency() {
        Not not = new Not();
        
        // 测试不同输入类型的返回类型一致性
        assertEquals(ValueType.BOOLEAN, not.returnType(BooleanConst.TRUE_CONST));
        assertEquals(ValueType.BOOLEAN, not.returnType(NumberConst.of("1")));
        assertEquals(ValueType.BOOLEAN, not.returnType(TextArg.of("test")));
    }
}