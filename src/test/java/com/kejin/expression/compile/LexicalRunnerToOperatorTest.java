package com.kejin.expression.compile;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.Operators;
import com.kejin.expression.operator.calculate.*;
import com.kejin.expression.operator.compare.*;
import com.kejin.expression.operator.placeholder.BrokersLeft;
import com.kejin.expression.operator.placeholder.BrokersRight;
import com.kejin.expression.operator.placeholder.Comma;
import com.kejin.expression.operator.single.BitNot;
import com.kejin.expression.operator.single.Not;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * LexicalRunner.toOperator方法的单元测试类
 * 专门测试操作符解析功能的各种场景
 */
public class LexicalRunnerToOperatorTest {

    /**
     * 测试单个有效的操作符
     */
    @Test
    public void testToOperator_SingleValidOperator() throws ExpressionException {
        char[] stack = "+".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Plus);
        assertEquals("+", result.get(0).symbol());
    }

    /**
     * 测试无效的单个字符操作符
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_InvalidSingleCharacter() throws ExpressionException {
        char[] stack = "x".toCharArray();
        LexicalRunner.toOperator(stack, 1);
    }

    /**
     * 测试空操作符
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_EmptyOperator() throws ExpressionException {
        char[] stack = new char[0];
        LexicalRunner.toOperator(stack, 0);
    }

    /**
     * 测试单个有效比较操作符
     */
    @Test
    public void testToOperator_SingleComparisonOperator() throws ExpressionException {
        char[] stack = "==".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Equal);
        assertEquals("==", result.get(0).symbol());
    }

    /**
     * 测试大于等于操作符
     */
    @Test
    public void testToOperator_GreaterThanOrEqual() throws ExpressionException {
        char[] stack = ">=".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof GreaterThanOE);
        assertEquals(">=", result.get(0).symbol());
    }

    /**
     * 测试小于等于操作符
     */
    @Test
    public void testToOperator_LessThanOrEqual() throws ExpressionException {
        char[] stack = "<=".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof LessThanOE);
        assertEquals("<=", result.get(0).symbol());
    }

    /**
     * 测试不等于操作符
     */
    @Test
    public void testToOperator_NotEqual() throws ExpressionException {
        char[] stack = "<>".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof NotEqual);
        assertEquals("<>", result.get(0).symbol());
    }

    /**
     * 测试乘法操作符
     */
    @Test
    public void testToOperator_Multiply() throws ExpressionException {
        char[] stack = "*".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Multiply);
        assertEquals("*", result.get(0).symbol());
    }

    /**
     * 测试除法操作符
     */
    @Test
    public void testToOperator_Divide() throws ExpressionException {
        char[] stack = "/".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Divide);
        assertEquals("/", result.get(0).symbol());
    }

    /**
     * 测试取模操作符
     */
    @Test
    public void testToOperator_Modulo() throws ExpressionException {
        char[] stack = "%".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Mod);
        assertEquals("%", result.get(0).symbol());
    }

    /**
     * 测试减法操作符
     */
    @Test
    public void testToOperator_Subtract() throws ExpressionException {
        char[] stack = "-".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Subtract);
        assertEquals("-", result.get(0).symbol());
    }

    /**
     * 测试按位与操作符
     */
    @Test
    public void testToOperator_BitwiseAnd() throws ExpressionException {
        char[] stack = "&".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BitAnd);
        assertEquals("&", result.get(0).symbol());
    }

    /**
     * 测试按位或操作符
     */
    @Test
    public void testToOperator_BitwiseOr() throws ExpressionException {
        char[] stack = "|".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BitOr);
        assertEquals("|", result.get(0).symbol());
    }

    /**
     * 测试按位异或操作符
     */
    @Test
    public void testToOperator_BitwiseXor() throws ExpressionException {
        char[] stack = "^".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BitXOR);
        assertEquals("^", result.get(0).symbol());
    }

    /**
     * 测试左移操作符
     */
    @Test
    public void testToOperator_LeftShift() throws ExpressionException {
        char[] stack = "<<".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof ShiftLeftWithSign);
        assertEquals("<<", result.get(0).symbol());
    }

    /**
     * 测试无符号右移操作符
     */
    @Test
    public void testToOperator_UnsignedRightShift() throws ExpressionException {
        char[] stack = ">>>".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 3);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof ShiftRight);
        assertEquals(">>>", result.get(0).symbol());
    }

    /**
     * 测试逻辑与操作符
     */
    @Test
    public void testToOperator_LogicalAnd() throws ExpressionException {
        char[] stack = "&&".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof And);
        assertEquals("&&", result.get(0).symbol());
    }

    /**
     * 测试逻辑或操作符
     */
    @Test
    public void testToOperator_LogicalOr() throws ExpressionException {
        char[] stack = "||".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Or);
        assertEquals("||", result.get(0).symbol());
    }

    /**
     * 测试逻辑非操作符
     */
    @Test
    public void testToOperator_LogicalNot() throws ExpressionException {
        char[] stack = "!".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Not);
        assertEquals("!", result.get(0).symbol());
    }

    /**
     * 测试按位取反操作符
     */
    @Test
    public void testToOperator_BitwiseNot() throws ExpressionException {
        char[] stack = "~".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BitNot);
        assertEquals("~", result.get(0).symbol());
    }

    /**
     * 测试左括号操作符
     */
    @Test
    public void testToOperator_LeftParenthesis() throws ExpressionException {
        char[] stack = "(".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BrokersLeft);
        assertEquals("(", result.get(0).symbol());
    }

    /**
     * 测试右括号操作符
     */
    @Test
    public void testToOperator_RightParenthesis() throws ExpressionException {
        char[] stack = ")".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BrokersRight);
        assertEquals(")", result.get(0).symbol());
    }

    /**
     * 测试逗号操作符
     */
    @Test
    public void testToOperator_Comma() throws ExpressionException {
        char[] stack = ",".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Comma);
        assertEquals(",", result.get(0).symbol());
    }

    /**
     * 测试复合操作符拆分 - 两个有效操作符连接
     */
    @Test
    public void testToOperator_TwoValidOperatorsCombined() throws ExpressionException {
        char[] stack = "++".toCharArray(); // 两个+操作符
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof Plus);
        assertTrue(result.get(1) instanceof Plus);
        assertEquals("+", result.get(0).symbol());
        assertEquals("+", result.get(1).symbol());
    }

    /**
     * 测试三个操作符的组合拆分
     */
    @Test
    public void testToOperator_ThreeOperatorsCombined() throws ExpressionException {
        char[] stack = "+++".toCharArray(); // 三个+操作符
        List<Operators> result = LexicalRunner.toOperator(stack, 3);
        
        assertEquals(3, result.size());
        for (Operators op : result) {
            assertTrue(op instanceof Plus);
            assertEquals("+", op.symbol());
        }
    }

    /**
     * 测试混合操作符组合
     */
    @Test
    public void testToOperator_MixedOperators() throws ExpressionException {
        char[] stack = "+-+".toCharArray(); // +, -, +
        List<Operators> result = LexicalRunner.toOperator(stack, 3);
        
        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof Plus);
        assertTrue(result.get(1) instanceof Subtract);
        assertTrue(result.get(2) instanceof Plus);
    }

    /**
     * 测试无效的多字符操作符
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_InvalidMultiCharOperator() throws ExpressionException {
        char[] stack = "xyz".toCharArray();
        LexicalRunner.toOperator(stack, 3);
    }

    /**
     * 测试部分有效部分无效的操作符组合
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_PartiallyValidCombination() throws ExpressionException {
        char[] stack = "+xy".toCharArray(); // +是有效的，但xy不是
        LexicalRunner.toOperator(stack, 3);
    }

    /**
     * 测试长字符串无效操作符
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_LongInvalidOperator() throws ExpressionException {
        char[] stack = "invalidoperator".toCharArray();
        LexicalRunner.toOperator(stack, 15);
    }

    /**
     * 测试操作符拆分边界情况
     */
    @Test
    public void testToOperator_BoundarySplitting() throws ExpressionException {
        // 测试能够被正确拆分的边界情况
        char[] stack = "><".toCharArray(); // > 和 <
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof GreaterThan);
        assertTrue(result.get(1) instanceof LessThan);
    }

    /**
     * 测试异常信息内容
     */
    @Test
    public void testToOperator_ExceptionMessage() {
        try {
            char[] stack = "xyz".toCharArray();
            LexicalRunner.toOperator(stack, 3);
            fail("应该抛出ExpressionException");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("运算符无法识别"));
            assertTrue(e.getMessage().contains("xyz"));
        }
    }

    /**
     * 测试操作符列表为空的情况
     */
    @Test(expected = ExpressionException.class)
    public void testToOperator_EmptyResultList() throws ExpressionException {
        // 构造一个理论上会返回空列表的情况
        char[] stack = new char[]{'@'}; // 假设@不是有效操作符
        LexicalRunner.toOperator(stack, 1);
    }

    /**
     * 测试最大长度操作符数组
     */
    @Test
    public void testToOperator_MaxLengthArray() throws ExpressionException {
        char[] stack = new char[128];
        for (int i = 0; i < 128; i += 2) {
            if (i + 1 < 128) {
                stack[i] = '+';
                stack[i + 1] = '-';
            }
        }
        // 只测试前几个字符以避免过长
        List<Operators> result = LexicalRunner.toOperator(stack, 10);
        
        assertEquals(10, result.size());
        for (int i = 0; i < 10; i += 2) {
            assertTrue(result.get(i) instanceof Plus);
            if (i + 1 < 10) {
                assertTrue(result.get(i + 1) instanceof Subtract);
            }
        }
    }
}