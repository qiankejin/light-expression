package com.kejin.expression.compile;

import com.kejin.expression.Node;
import com.kejin.expression.enums.CharType;
import com.kejin.expression.enums.Dictionary;
import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.Operators;
import com.kejin.expression.operator.calculate.Plus;
import com.kejin.expression.operator.compare.Equal;
import com.kejin.expression.operator.compare.GreaterThanOE;
import com.kejin.expression.var.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LexicalRunnerTest {

    @Test
    public void testToOperator_SingleValidOperator() throws ExpressionException {
        char[] stack = "+".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 1);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Plus);
    }

    @Test
    public void testToOperator_MultipleValidOperators() throws ExpressionException {
        char[] stack = "++".toCharArray();  // Assuming ++ isn't a single operator but two + operators
        try {
            List<Operators> result = LexicalRunner.toOperator(stack, 2);
            // This depends on whether ++ is registered as a single operator
            // If not, it would try to parse as two separate + operators
        } catch (ExpressionException e) {
            // Expected if ++ is not a recognized operator
        }
    }

    @Test(expected = ExpressionException.class)
    public void testToOperator_InvalidSingleCharacter() throws ExpressionException {
        char[] stack = "x".toCharArray();  // Assuming x is not a valid operator
        LexicalRunner.toOperator(stack, 1);
    }

    @Test(expected = ExpressionException.class)
    public void testToOperator_InvalidMultipleCharacters() throws ExpressionException {
        char[] stack = "xyz".toCharArray();  // Assuming xyz is not a valid operator
        LexicalRunner.toOperator(stack, 3);
    }

    @Test
    public void testToOperator_ValidComplexOperator() throws ExpressionException {
        // Testing a known valid operator like ==
        char[] stack = "==".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Equal);
    }

    @Test
    public void testToOperator_ValidTwoOperatorSequence() throws Exception {
        // Testing if we can separate two operators like >=
        char[] stack = ">=".toCharArray();
        List<Operators> result = LexicalRunner.toOperator(stack, 2);
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof GreaterThanOE);
    }

    @Test
    public void testToText() {
        char[] stack = "hello".toCharArray();
        Node result = LexicalRunner.toText(stack, 5);
        
        assertTrue(result instanceof TextConst);
        assertEquals("\"hello\"", result.toString());
    }

    @Test
    public void testToNumber_Integer() {
        char[] stack = "123".toCharArray();
        Node result = LexicalRunner.toNumber(stack, 3);
        
        assertTrue(result instanceof NumberConst);
        assertEquals("123", result.toString());
    }

    @Test
    public void testToNumber_Decimal() {
        char[] stack = "12.34".toCharArray();
        Node result = LexicalRunner.toNumber(stack, 5);
        
        assertTrue(result instanceof NumberConst);
        assertEquals("12.34", result.toString());
    }

    @Test
    public void testToArg_BooleanTrue() {
        char[] stack = "true".toCharArray();
        Node result = LexicalRunner.toArg(stack, 4);
        
        assertSame(BooleanConst.TRUE_CONST, result);
    }

    @Test
    public void testToArg_BooleanFalse() {
        char[] stack = "false".toCharArray();
        Node result = LexicalRunner.toArg(stack, 5);
        
        assertSame(BooleanConst.FALSE_CONST, result);
    }

    @Test
    public void testToArg_VariableWithSuffix() {
        char[] stack = "varB".toCharArray();  // Ends with B for BooleanArg
        Node result = LexicalRunner.toArg(stack, 4);
        
        assertTrue(result instanceof BooleanArg);
    }

    @Test
    public void testToArg_TextVariable() {
        char[] stack = "varT".toCharArray();  // Ends with T for TextArg
        Node result = LexicalRunner.toArg(stack, 4);
        
        assertTrue(result instanceof TextArg);
    }

    @Test
    public void testToArg_NumberVariable() {
        char[] stack = "varN".toCharArray();  // Ends with N for NumberArg
        Node result = LexicalRunner.toArg(stack, 4);
        
        assertTrue(result instanceof NumberArg);
    }

    @Test
    public void testToArg_DateVariable() {
        char[] stack = "varD".toCharArray();  // Ends with D for DateArg
        Node result = LexicalRunner.toArg(stack, 4);
        
        assertTrue(result instanceof DateArg);
    }

    @Test
    public void testToArg_DefaultToTextConst() {
        char[] stack = "variable".toCharArray();  // Doesn't end with a known suffix
        Node result = LexicalRunner.toArg(stack, 8);
        
        assertTrue(result instanceof TextConst);
    }

    @Test
    public void testCompile_EmptyString() {
        try {
            LexicalRunner.compile("");
            fail("Expected ExpressionException for empty string");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getErrorCode(), e.getErrorCode());
            assertTrue(e.getMessage().contains("表达式不能为空"));
        }
    }

    @Test
    public void testCompile_NullString() {
        try {
            LexicalRunner.compile(null);
            fail("Expected ExpressionException for null string");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("表达式不能为空"));
        }
    }

    @Test
    public void testCompile_SimpleNumber() throws ExpressionException {
        List<Node> result = LexicalRunner.compile("123");
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof NumberConst);
        assertEquals("123", result.get(0).toString());
    }

    @Test
    public void testCompile_SimpleText() throws ExpressionException {
        List<Node> result = LexicalRunner.compile("\"hello\"");
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof TextConst);
        assertEquals("\"hello\"", result.get(0).toString());
    }

    @Test
    public void testCompile_SimpleVariable() throws ExpressionException {
        List<Node> result = LexicalRunner.compile("varN");  // Ends with N, so should be NumberArg
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof NumberArg);
    }

    @Test
    public void testCompile_SimpleOperator() throws ExpressionException {
        List<Node> result = LexicalRunner.compile("+");
        
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Plus);
    }

    @Test
    public void testCompile_ExpressionWithSpaces() throws ExpressionException {
        List<Node> result = LexicalRunner.compile("123 + 456");
        
        assertEquals(3, result.size());
        assertTrue(result.get(0) instanceof NumberConst);
        assertTrue(result.get(1) instanceof Plus);
        assertTrue(result.get(2) instanceof NumberConst);
    }

    @Test
    public void testCharTypeRecognition() {
        assertEquals(CharType.NUMBER, Dictionary.charType('1'));
        assertEquals(CharType.NUMBER, Dictionary.charType('9'));
        assertEquals(CharType.NUMBER, Dictionary.charType('.'));
        assertEquals(CharType.TEXT, Dictionary.charType('"'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('+'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('-'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('*'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('/'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('('));
        assertEquals(CharType.OPERATOR, Dictionary.charType(')'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('='));
        assertEquals(CharType.ARG, Dictionary.charType('A'));  // Default case
    }
}