package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

/**
 * 精简版复杂表达式集成测试
 * 专注于表达式引擎实际支持的语法组合
 */
public class SimplifiedComplexExpressionTest {

    /**
     * 测试基本的算术表达式组合
     */
    @Test
    public void testBasicArithmeticCombinations() throws ExpressionException {
        // 基本运算符组合
        TestUtil.execute("2+3*4", 14);
        TestUtil.execute("(2+3)*4", 20);
        TestUtil.execute("10-6/2", 7);
        TestUtil.execute("20/(4+1)", 4);
        
        // 负号处理
        TestUtil.execute("-5+10", 5);
        TestUtil.execute("10-(-5)", 15);
        
        // 参数变量运算
        TestUtil.execute("I01N+I02N+I03N", 6);
        TestUtil.execute("I05N*I02N-I01N", 9);
    }

    /**
     * 测试比较运算符组合
     */
    @Test
    public void testComparisonOperatorCombinations() throws ExpressionException {
        TestUtil.execute("5>3", true);
        TestUtil.execute("5>=5", true);
        TestUtil.execute("3<5", true);
        TestUtil.execute("3<=3", true);
        TestUtil.execute("5==5", true);
        TestUtil.execute("5<>3", true);
        
        // 与算术运算结合
        TestUtil.execute("2+3>1+3", true);
        TestUtil.execute("10/2==5", true);
    }

    /**
     * 测试逻辑运算符组合
     */
    @Test
    public void testLogicalOperatorCombinations() throws ExpressionException {
        TestUtil.execute("true&&true", true);
        TestUtil.execute("true&&false", false);
        TestUtil.execute("false||true", true);
        TestUtil.execute("false||false", false);
        TestUtil.execute("!true", false);
        TestUtil.execute("!false", true);
        
        // 与比较运算结合
        TestUtil.execute("5>3&&2<4", true);
        TestUtil.execute("10==10||5>10", true);
    }

    /**
     * 测试函数调用的基本组合
     */
    @Test
    public void testFunctionBasicCombinations() throws ExpressionException {
        // 数学函数
        TestUtil.execute("ROUND_UP(SQRT(16),2)", 4.0);
        TestUtil.execute("MAX(1,5,3,8,2)", 8);
        TestUtil.execute("MIN(10,5,15,3)", 3);
        TestUtil.execute("POW(2,3)", 8.0);
        
        // 三角函数
        TestUtil.execute("ROUND_UP(SIN(0),4)", 0.0);
        TestUtil.execute("ROUND_UP(COS(0),4)", 1.0);
        
        // 字符串函数
        TestUtil.execute("CONCAT(\"Hello\",\"World\")", "HelloWorld");
        TestUtil.execute("CONCAT(\"Value:\",42)", "Value:42");
    }

    /**
     * 测试条件函数组合
     */
    @Test
    public void testConditionalFunctionCombinations() throws ExpressionException {
        TestUtil.execute("IF(true,10,20)", 10);
        TestUtil.execute("IF(false,10,20)", 20);
        TestUtil.execute("IF(5>3,\"Greater\",\"Less\")", "Greater");
        
        // 与变量结合
        TestUtil.execute("IF(B01B,I01N,I02N)", 1);
        TestUtil.execute("IF(B02B,I01N,I02N)", 2);
        
        // SWITCH函数
        TestUtil.execute("SWITCH(I04N,1:\"a\",2:\"b\",3:\"c\",4:\"d\",5:\"e\")", "d");
        TestUtil.execute("SWITCH(3,1:\"one\",2:\"two\",3:\"three\",4:\"four\")", "three");
    }

    /**
     * 测试参数变量的组合使用
     */
    @Test
    public void testParameterCombinations() throws ExpressionException {
        // 整数参数
        TestUtil.execute("I01N+I02N+I03N", 6);
        TestUtil.execute("I05N*I02N-I01N", 9);
        TestUtil.execute("MAX(I01N,I02N,I06N)", 6);
        
        // 小数参数
        TestUtil.execute("N01N+N02N", 3.0);
        TestUtil.execute("N05N*N02N", 10.0);
        
        // 布尔参数
        TestUtil.execute("B01B&&true", true);
        TestUtil.execute("B02B||false", false);
        TestUtil.execute("!(B01B)", false);
        
        // 混合使用
        TestUtil.execute("IF(B01B,I01N+N01N,I02N+N02N)", 2.0);
        TestUtil.execute("CONCAT(T01T,T02T)", "12");
    }

    /**
     * 测试边界条件和特殊情况
     */
    @Test
    public void testBoundaryConditions() throws ExpressionException {
        // 零值运算
        TestUtil.execute("0+5", 5);
        TestUtil.execute("10-0", 10);
        TestUtil.execute("0*100", 0);
        TestUtil.execute("IF(0==0,\"zero\",\"non-zero\")", "zero");
        
        // 负数运算
        TestUtil.execute("-5+3", -2);
        TestUtil.execute("10-(-5)", 15);
        TestUtil.execute("-3*(-4)", 12);
        
        // 大数值
        TestUtil.execute("1000000+500000", 1500000);
    }

    /**
     * 测试实际可用的复杂组合表达式
     */
    @Test
    public void testWorkingComplexCombinations() throws ExpressionException {
        // 算术与条件结合
        TestUtil.execute("IF(I01N>0,I01N+I02N,I03N)", 3);
        TestUtil.execute("IF(I01N+I02N>I03N,I04N*2,I05N)", 5);
        
        // 函数嵌套调用
        TestUtil.execute("ROUND_UP(SQRT(POW(I03N,2)),2)", 3.0);
        TestUtil.execute("CONCAT(\"Result:\",ROUND_UP(SQRT(25),2))", "Result:5.00");
        
        // 多层条件判断
        TestUtil.execute("IF(I01N>0,IF(I02N>I01N,\"Increasing\",\"Stable\"),\"Negative\")", "Increasing");
        
        // 实际业务场景示例
        TestUtil.execute("IF(B01B,MAX(I01N,I02N,I03N),MIN(I04N,I05N,I06N))", 3);
        TestUtil.execute("CONCAT(\"Score:\",IF(I04N>=4,\"Pass\",\"Fail\"))", "Score:Pass");
    }
}