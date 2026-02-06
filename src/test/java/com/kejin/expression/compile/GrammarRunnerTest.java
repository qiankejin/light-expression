package com.kejin.expression.compile;

import com.kejin.TestUtil;
import com.kejin.expression.ExpressCompiler;
import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

import static org.junit.Assert.*;

public class GrammarRunnerTest {
    @Test
    public void testBroker(){
        TestUtil.execute("MIN(1,INVER(2))",-2);
    }

    /**
     * 测试语法错误：缺少右括号
     * 验证GrammarRunner能否正确识别未闭合的括号
     */
    @Test
    public void testSyntaxError_MissingRightParenthesis() {
        try {
            ExpressCompiler.compile("(1+2*(3+4)");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("括号未正确关闭"));
        }
    }

    /**
     * 测试语法错误：多余的右括号
     * 验证GrammarRunner能否检测到多余的括号
     */
    @Test
    public void testSyntaxError_ExtraRightParenthesis() {
        try {
            ExpressCompiler.compile("1+2)*3");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("语法错误") || e.getMessage().contains("括号"));
        }
    }

    /**
     * 测试语法错误：连续的操作符
     * 验证GrammarRunner能否识别无效的操作符序列
     */
    @Test
    public void testSyntaxError_ConsecutiveOperators() {
        try {
            ExpressCompiler.compile("1++2");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能连续出现") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能连续出现") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：操作符在开头
     * 验证GrammarRunner能否检测到无效的表达式开头
     */
    @Test
    public void testSyntaxError_OperatorAtStart() {
        try {
            ExpressCompiler.compile("+1+2");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能出现在开头") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能出现在开头") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：缺少操作数
     * 验证GrammarRunner能否识别缺少必要操作数的情况
     */
    @Test
    public void testSyntaxError_MissingOperand() {
        try {
            ExpressCompiler.compile("1+");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目运算符缺少右边参数") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：缺少左操作数
     * 验证GrammarRunner能否识别缺少左操作数的情况
     */
    @Test
    public void testSyntaxError_MissingLeftOperand() {
        try {
            ExpressCompiler.compile("*2");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("运算符缺少左边变量") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能出现在开头") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：函数缺少参数
     * 验证GrammarRunner能否识别函数调用中的参数缺失
     */
    @Test
    public void testSyntaxError_FunctionMissingParameters() {
        try {
            ExpressCompiler.compile("MAX(");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("函数缺少右边参数") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：单目运算符缺少操作数
     * 验证GrammarRunner能否识别单目运算符的语法错误
     */
    @Test
    public void testSyntaxError_UnaryOperatorMissingOperand() {
        try {
            ExpressCompiler.compile("!");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("单目运算符缺少右边参数") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：空表达式
     * 验证GrammarRunner能否处理空表达式
     */
    @Test
    public void testSyntaxError_EmptyExpression() {
        try {
            ExpressCompiler.compile("");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("表达式不能为空") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：无效的参数列表
     * 验证GrammarRunner能否识别参数列表中的语法错误
     */
    @Test
    public void testSyntaxError_InvalidArgumentList() {
        try {
            ExpressCompiler.compile("MAX(1,,2)");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("参数列表逗号位置不对") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("参数列表逗号位置不对") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：嵌套括号错误
     * 验证GrammarRunner能否处理复杂的括号嵌套错误
     */
    @Test
    public void testSyntaxError_NestedParenthesesError() {
        try {
            ExpressCompiler.compile("((1+2)*3");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("括号未正确关闭") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：无法识别的语法错误
     * 验证GrammarRunner能否处理无法识别的语法结构
     */
    @Test
    public void testSyntaxError_UnrecognizedSyntax() {
        try {
            ExpressCompiler.compile("@#$%^&*");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("出现无法识别的语法错误") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能连续出现") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：函数参数列表错误
     * 验证GrammarRunner能否识别函数参数列表中的语法错误
     */
    @Test
    public void testSyntaxError_FunctionParameterListError() {
        try {
            ExpressCompiler.compile("MAX(1,,2)");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("参数列表逗号位置不对") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("参数列表逗号位置不对") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：单目运算符位置错误
     * 验证GrammarRunner能否识别单目运算符的错误位置
     */
    @Test
    public void testSyntaxError_UnaryOperatorWrongPosition() {
        try {
            ExpressCompiler.compile("1 !");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("单目运算符不能出现在变量后面") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：单目运算符语法错误
     * 验证GrammarRunner能否识别单目运算符的语法错误
     */
    @Test
    public void testSyntaxError_UnaryOperatorSyntaxError() {
        try {
            ExpressCompiler.compile("!+");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("单目运算符语法不对") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：函数语法错误
     * 验证GrammarRunner能否识别函数调用的语法错误
     */
    @Test
    public void testSyntaxError_FunctionSyntaxError() {
        try {
            ExpressCompiler.compile("MAX+(1,2)");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("函数语法不对") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：双目运算符语法错误
     * 验证GrammarRunner能否识别双目运算符的语法错误
     */
    @Test
    public void testSyntaxError_BinaryOperatorSyntaxError() {
        try {
            ExpressCompiler.compile("1+*2");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目运算符语法不对") || 
                      e.getMessage().contains("语法错误"));
        } catch (com.kejin.expression.errors.ExecuteException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("双目操作符不能连续出现") || 
                      e.getMessage().contains("语法错误"));
        }
    }

    /**
     * 测试语法错误：节点不能单独存在
     * 验证GrammarRunner能否识别无效的独立节点
     */
    @Test
    public void testSyntaxError_NodeCannotExistAlone() {
        try {
            ExpressCompiler.compile(",");
            fail("应该抛出语法错误异常");
        } catch (ExpressionException e) {
            assertEquals(ErrorCode.GRAMMAR_ERROR.getCode(), e.getCode());
            assertTrue(e.getMessage().contains("不能单独存在") || 
                      e.getMessage().contains("语法错误"));
        }
    }
}