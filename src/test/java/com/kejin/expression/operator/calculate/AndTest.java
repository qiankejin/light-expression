package com.kejin.expression.operator.calculate;

import com.kejin.TestUtil;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.BooleanOperator;
import com.kejin.expression.value.BooleanValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.BooleanArg;
import com.kejin.expression.var.BooleanConst;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndTest {

    @Test
    public void testAndOperatorBasicFunctionality() {
        And and = new And();
        
        // 测试基本属性
        assertEquals("&&", and.symbol());
        assertEquals(10, and.priority()); // LEVEL_10
        
        // 测试返回类型
        Var left = BooleanConst.TRUE_CONST;
        Var right = BooleanConst.FALSE_CONST;
        assertEquals(ValueType.BOOLEAN, and.returnType(left, right));
    }

    @Test
    public void testAndOperatorCalculation() {
        And and = new And();
        
        // 测试 true && true = true
        Value<?> result1 = and.operatorCalculate(BooleanValue.TRUE, BooleanValue.TRUE);
        assertTrue(result1.toBoolean());
        
        // 测试 true && false = false
        Value<?> result2 = and.operatorCalculate(BooleanValue.TRUE, BooleanValue.FALSE);
        assertFalse(result2.toBoolean());
        
        // 测试 false && true = false
        Value<?> result3 = and.operatorCalculate(BooleanValue.FALSE, BooleanValue.TRUE);
        assertFalse(result3.toBoolean());
        
        // 测试 false && false = false
        Value<?> result4 = and.operatorCalculate(BooleanValue.FALSE, BooleanValue.FALSE);
        assertFalse(result4.toBoolean());
    }

    @Test
    public void testAndWithBooleanArguments() {
        // 测试使用布尔参数的实际表达式计算
        TestUtil.execute("true&&true", true);
        TestUtil.execute("true&&false", false);
        TestUtil.execute("false&&true", false);
        TestUtil.execute("false&&false", false);
    }

    @Test
    public void testAndWithBooleanConstantsOnly() {
        And and = new And();
        
        // 只测试明确的布尔值，避免类型转换问题
        Value<?> result1 = and.operatorCalculate(Value.of(true), Value.of(true));
        assertTrue(result1.toBoolean());
        
        Value<?> result2 = and.operatorCalculate(Value.of(false), Value.of(true));
        assertFalse(result2.toBoolean());
        
        Value<?> result3 = and.operatorCalculate(Value.of(true), Value.of(false));
        assertFalse(result3.toBoolean());
        
        Value<?> result4 = and.operatorCalculate(Value.of(false), Value.of(false));
        assertFalse(result4.toBoolean());
    }

    @Test
    public void testAndOperatorPrecedence() {
        // 测试运算符优先级
        // && 的优先级是 10，比 || (11) 高，但比比较运算符 (5) 低
        And and = new And();
        assertEquals(10, and.priority());
    }

    @Test
    public void testAndWithSimpleExpressions() {
        // 测试简单表达式组合
        TestUtil.execute("true&&true", true);
        TestUtil.execute("true&&false", false);
        TestUtil.execute("false&&true", false);
        TestUtil.execute("false&&false", false);
    }

    @Test
    public void testAndOperatorInheritance() {
        And and = new And();
        assertTrue(and instanceof BooleanOperator);
    }

    @Test
    public void testAndWithNullValues() {
        And and = new And();
        
        // 测试null值处理
        try {
            and.operatorCalculate(Value.of((Object)null), Value.of(true));
            fail("Should throw exception for null conversion");
        } catch (RuntimeException e) {
            // Expected - null cannot be converted to boolean
        }
    }

    @Test
    public void testAndOperatorSymbol() {
        And and = new And();
        assertEquals("&&", and.symbol());
    }
}