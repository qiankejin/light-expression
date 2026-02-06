package com.kejin.expression.operator.calculate;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.param.ParamCollection;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Eval操作符单元测试类
 * 测试赋值表达式的各种场景和边界情况
 */
public class EvalTest {

    private Eval evalOperator;
    private ParamCollection paramCollection;

    @Before
    public void setUp() {
        evalOperator = new Eval();
        paramCollection = new MapParam();
    }

    /**
     * 测试基本的数值赋值功能
     */
    @Test
    public void testBasicNumberAssignment() throws ExpressionException {
        // 创建左侧变量和右侧常量
        NumberArg leftVar = NumberArg.of("result");
        NumberConst rightConst = NumberConst.of("42");
        
        // 执行赋值操作
        Value<?> result = evalOperator.calculate(leftVar, rightConst, paramCollection);
        
        // 验证返回值
        assertEquals(42L, result.toLong());
        
        // 验证变量已正确存储在参数集合中
        Value<?> storedValue = paramCollection.get("result");
        assertEquals(42L, storedValue.toLong());
        
        // 验证符号
        assertEquals("=", evalOperator.symbol());
        
        // 验证优先级
        assertEquals(12, evalOperator.priority());
    }

    /**
     * 测试文本赋值功能
     */
    @Test
    public void testTextAssignment() throws ExpressionException {
        TextArg leftVar = TextArg.of("message");
        TextConst rightConst = TextConst.of("Hello World");
        
        Value<?> result = evalOperator.calculate(leftVar, rightConst, paramCollection);
        
        assertEquals("Hello World", result.toString());
        assertEquals("Hello World", paramCollection.get("message").toString());
    }

    /**
     * 测试布尔值赋值功能
     */
    @Test
    public void testBooleanAssignment() throws ExpressionException {
        BooleanArg leftVar = BooleanArg.of("flag");
        BooleanConst rightConst = BooleanConst.TRUE_CONST;
        
        Value<?> result = evalOperator.calculate(leftVar, rightConst, paramCollection);
        
        assertTrue(result.toBoolean());
        assertTrue(paramCollection.get("flag").toBoolean());
    }

    /**
     * 测试左侧不是变量时的异常情况
     */
    @Test(expected = ExpressionException.class)
    public void testLeftNotVariableException() throws ExpressionException {
        // 使用常量作为左侧，应该抛出异常
        NumberConst leftConst = NumberConst.of("10");
        NumberConst rightConst = NumberConst.of("20");
        
        evalOperator.check(leftConst, rightConst);
    }

    /**
     * 测试左右类型不匹配时的异常情况
     */
    @Test(expected = ExpressionException.class)
    public void testTypeMismatchException() throws ExpressionException {
        // 数值变量赋值文本值，应该抛出异常
        NumberArg leftVar = NumberArg.of("numberVar");
        TextConst rightConst = TextConst.of("textValue");
        
        evalOperator.check(leftVar, rightConst);
    }

    /**
     * 测试返回类型正确性
     */
    @Test
    public void testReturnType() {
        NumberArg leftVar = NumberArg.of("num");
        TextConst rightConst = TextConst.of("text");
        
        // 虽然这会导致类型检查失败，但我们可以测试返回类型方法
        ValueType returnType = evalOperator.returnType(leftVar, rightConst);
        assertEquals(ValueType.NUMBER, returnType);
    }

    /**
     * 测试复杂表达式的赋值
     */
    @Test
    public void testComplexExpressionAssignment() throws ExpressionException {
        NumberArg resultVar = NumberArg.of("calculated");
        // 模拟一个简单的加法表达式 10 + 5
        NumberConst leftOperand = NumberConst.of("10");
        NumberConst rightOperand = NumberConst.of("5");
        
        // 为了简化测试，我们直接使用加法操作符的结果
        Plus plusOperator = new Plus();
        Value<?> calculationResult = plusOperator.operatorCalculate(
            leftOperand.execute(paramCollection), 
            rightOperand.execute(paramCollection)
        );
        
        // 创建一个模拟的表达式变量来包装计算结果
        NumberConst calcResultConst = NumberConst.of(calculationResult.toString());
        
        Value<?> assignmentResult = evalOperator.calculate(resultVar, calcResultConst, paramCollection);
        
        assertEquals(15L, assignmentResult.toLong());
        assertEquals(15L, paramCollection.get("calculated").toLong());
    }

    /**
     * 测试operatorCalculate方法返回null
     */
    @Test
    public void testOperatorCalculateReturnsNull() {
        Value<?> result = evalOperator.operatorCalculate(null, null);
        assertNull(result);
    }

    /**
     * 测试连续赋值操作
     */
    @Test
    public void testChainedAssignments() throws ExpressionException {
        // 第一次赋值: a = 100
        NumberArg aVar = NumberArg.of("a");
        NumberConst hundred = NumberConst.of("100");
        evalOperator.calculate(aVar, hundred, paramCollection);
        
        // 第二次赋值: b = a (从参数集合中获取a的值)
        NumberArg bVar = NumberArg.of("b");
        // 创建一个新的变量来引用a的值
        NumberArg aReference = NumberArg.of("a");
        Value<?> aValue = aReference.execute(paramCollection);
        
        // 创建临时常量来模拟a的当前值
        NumberConst aCurrentValue = NumberConst.of(aValue.toString());
        evalOperator.calculate(bVar, aCurrentValue, paramCollection);
        
        // 验证两个变量都有相同的值
        assertEquals(100L, paramCollection.get("a").toLong());
        assertEquals(100L, paramCollection.get("b").toLong());
    }

    /**
     * 测试空参数集合的情况
     */
    @Test
    public void testWithEmptyParamCollection() throws ExpressionException {
        ParamCollection emptyParams = new MapParam();
        TextArg messageVar = TextArg.of("message");
        TextConst greeting = TextConst.of("Welcome");
        
        Value<?> result = evalOperator.calculate(messageVar, greeting, emptyParams);
        
        assertEquals("Welcome", result.toString());
        assertEquals("Welcome", emptyParams.get("message").toString());
    }

    /**
     * 测试重复赋值同一变量
     */
    @Test
    public void testReassignSameVariable() throws ExpressionException {
        NumberArg counterVar = NumberArg.of("counter");
        
        // 第一次赋值
        NumberConst initialValue = NumberConst.of("1");
        evalOperator.calculate(counterVar, initialValue, paramCollection);
        assertEquals(1L, paramCollection.get("counter").toLong());
        
        // 第二次赋值覆盖
        NumberConst newValue = NumberConst.of("42");
        evalOperator.calculate(counterVar, newValue, paramCollection);
        assertEquals(42L, paramCollection.get("counter").toLong());
    }

    /**
     * 测试边界值赋值
     */
    @Test
    public void testBoundaryValues() throws ExpressionException {
        // 测试大数值（使用较小的大数避免解析问题）
        NumberArg largeVar = NumberArg.of("large_num");
        NumberConst largeConst = NumberConst.of("999999999");
        
        Value<?> result = evalOperator.calculate(largeVar, largeConst, paramCollection);
        assertEquals(999999999L, result.toLong());
        assertEquals(999999999L, paramCollection.get("large_num").toLong());
        
        // 测试零值
        NumberArg zeroVar = NumberArg.of("zero");
        NumberConst zeroConst = NumberConst.of("0");
        Value<?> zeroResult = evalOperator.calculate(zeroVar, zeroConst, paramCollection);
        assertEquals(0L, zeroResult.toLong());
    }

    /**
     * 测试完整的check流程
     */
    @Test
    public void testCompleteCheckProcess() throws ExpressionException {
        NumberArg validLeft = NumberArg.of("valid");
        NumberConst validRight = NumberConst.of("123");
        
        // 应该不抛出异常
        evalOperator.check(validLeft, validRight);
        
        // 验证返回类型
        assertEquals(ValueType.NUMBER, evalOperator.returnType(validLeft, validRight));
    }

    /**
     * 测试不同数据类型的赋值组合
     */
    @Test
    public void testDifferentDataTypeCombinations() throws ExpressionException {
        // 测试所有支持的数据类型组合
        
        // 数值类型
        NumberArg numVar = NumberArg.of("num_result");
        NumberConst numVal = NumberConst.of("3.14");
        evalOperator.calculate(numVar, numVal, paramCollection);
        assertEquals(3.14, paramCollection.get("num_result").toDouble(), 0.001);
        
        // 文本类型
        TextArg textVar = TextArg.of("text_result");
        TextConst textVal = TextConst.of("测试文本");
        evalOperator.calculate(textVar, textVal, paramCollection);
        assertEquals("测试文本", paramCollection.get("text_result").toString());
        
        // 布尔类型
        BooleanArg boolVar = BooleanArg.of("bool_result");
        BooleanConst boolVal = BooleanConst.FALSE_CONST;
        evalOperator.calculate(boolVar, boolVal, paramCollection);
        assertFalse(paramCollection.get("bool_result").toBoolean());
    }
}