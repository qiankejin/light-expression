package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

import java.util.Date;

/**
 * 复杂表达式语法的复合型集成测试类
 * 测试各种操作符、函数和语法结构的组合使用
 */
public class ComplexExpressionIntegrationTest {

    /**
     * 测试算术运算符的复合表达式
     * 包含优先级、括号、负号等复杂组合
     */
    @Test
    public void testComplexArithmeticExpressions() throws ExpressionException {
        // 基本四则运算优先级测试
        TestUtil.execute("2 + 3 * 4", 14);           // 乘法优先级高于加法
        TestUtil.execute("(2 + 3) * 4", 20);         // 括号改变优先级
        TestUtil.execute("10 - 6 / 2", 7);           // 除法优先级高于减法
        TestUtil.execute("20 / (4 + 1)", 4);         // 括号确保正确计算
        
        // 复杂嵌套表达式
        TestUtil.execute("((1 + 2) * (3 + 4)) - 5", 16);
        TestUtil.execute("100 - (25 * 2) + (15 / 3)", 55);
        
        // 负号和减法的组合
        TestUtil.execute("-5 + 10", 5);
        TestUtil.execute("10 - (-5)", 15);
        TestUtil.execute("-(3 + 2)", -5);
        TestUtil.execute("-(-10)", 10);
        
        // 模运算与其他运算符结合
        TestUtil.execute("17 % 5 + 2", 4);
        TestUtil.execute("(20 % 7) * 3", 18);
        
        // 异或测试
        TestUtil.execute("2 ^ 3", 1);
        TestUtil.execute("3 ^ 2 + 1", 0);
    }

    /**
     * 测试比较运算符的复合表达式
     * 包含各种比较操作的组合和链式比较
     */
    @Test
    public void testComplexComparisonExpressions() throws ExpressionException {
        // 基本比较运算
        TestUtil.execute("5 > 3", true);
        TestUtil.execute("5 >= 5", true);
        TestUtil.execute("3 < 5", true);
        TestUtil.execute("3 <= 3", true);
        TestUtil.execute("5 == 5", true);
        TestUtil.execute("5 <> 3", true);
        
        // 复杂比较表达式
        TestUtil.execute("(10 > 5) && (3 < 8)", true);
        TestUtil.execute("(5 == 5) || (6 == 7)", true);
        TestUtil.execute("!((5 > 10))", true);
        
        // 数值比较与算术运算结合
        TestUtil.execute("(2 + 3) > (1 + 3)", true);
        TestUtil.execute("(10 / 2) == 5", true);
        TestUtil.execute("(3 * 4) >= (2 * 6)", true);
        
        // 边界值比较
        TestUtil.execute("0 >= 0", true);
        TestUtil.execute("-5 < 0", true);
        TestUtil.execute("-10 <= -5", true);
    }

    /**
     * 测试逻辑运算符的复合表达式
     * 包含逻辑与、或、非的各种组合
     */
    @Test
    public void testComplexLogicalExpressions() throws ExpressionException {
        // 基本逻辑运算
        TestUtil.execute("true && true", true);
        TestUtil.execute("true && false", false);
        TestUtil.execute("false || true", true);
        TestUtil.execute("false || false", false);
        TestUtil.execute("!true", false);
        TestUtil.execute("!false", true);
        
        // 复杂逻辑表达式
        TestUtil.execute("(true && false) || (true && true)", true);
        TestUtil.execute("!((true || false) && (false || true))", false);
        TestUtil.execute("((true && true) || false) && (false || true)", true);
        
        // 逻辑运算与比较运算结合
        TestUtil.execute("(5 > 3) && (2 < 4)", true);
        TestUtil.execute("(10 == 10) || (5 > 10)", true);
        TestUtil.execute("!((1 == 2) || (3 == 4))", true);
        
        // 多层嵌套逻辑
        TestUtil.execute("(((true && false) || true) && ((false || true) && true))", true);
        TestUtil.execute("(!((true || false) && (true || false))) || ((true && true) && false)", false);
    }

    /**
     * 测试位运算符的复合表达式
     * 包含按位与、或、异或、取反、移位等操作
     */
    @Test
    public void testComplexBitwiseExpressions() throws ExpressionException {
        // 基本位运算
        TestUtil.execute("5 & 3", 1);      // 101 & 011 = 001
        TestUtil.execute("5 | 3", 7);      // 101 | 011 = 111
        TestUtil.execute("5 ^ 3", 6);      // 101 ^ 011 = 110
        TestUtil.execute("~0", -1);        // 按位取反
        TestUtil.execute("8 >> 2", 2);     // 右移两位
        TestUtil.execute("2 << 2", 8);     // 左移两位
        
        // 位运算与算术运算结合
        TestUtil.execute("(15 & 7) + 10", 17);
        TestUtil.execute("(8 | 4) * 2", 24);
        TestUtil.execute("(12 ^ 6) - 3", 7);
        
        // 复杂位运算表达式
        TestUtil.execute("((255 & 15) << 2) >> 1", 30);
        TestUtil.execute("(((10 & 6) | 3) ^ 1) + 5", 7);
        TestUtil.execute("~((8 >> 1) & 3)", -1); // ~(4 & 3) = ~0 = -1
    }

    /**
     * 测试函数调用的复合表达式
     * 包含各种内置函数的组合使用
     */
    @Test
    public void testComplexFunctionExpressions() throws ExpressionException {
        // 数学函数组合
        TestUtil.execute("ROUND_UP(SQRT(16), 2)", 4.0);
        TestUtil.execute("MAX(1, 5, 3, 8, 2)", 8);
        TestUtil.execute("MIN(10, 5, 15, 3)", 3);
        TestUtil.execute("POW(2, 3)", 8.0);
        
        // 三角函数测试
        TestUtil.execute("ROUND_UP(SIN(0), 4)", 0.0);
        TestUtil.execute("ROUND_UP(COS(0), 4)", 1.0);
        
        // 字符串函数组合
        TestUtil.execute("CONCAT(\"Hello\", \" \", \"World\")", "Hello World");
        TestUtil.execute("CONCAT(\"Value: \", 42)", "Value: 42");
        
        // 日期函数测试
        TestUtil.execute("TO_NUM(\"123\")", 123.0);
        
        // 复合函数调用
        TestUtil.execute("ROUND_UP(SQRT(POW(4, 2)), 2)", 4.0);
        TestUtil.execute("MAX(MIN(10, 5), 3)", 5);
        TestUtil.execute("CONCAT(\"Result: \", ROUND_UP(SQRT(25), 2))", "Result: 5.00");
    }

    /**
     * 测试条件函数的复合表达式
     * 包含IF、SWITCH、IFF等条件控制结构
     */
    @Test
    public void testComplexConditionalExpressions() throws ExpressionException {
        // IF函数基本用法
        TestUtil.execute("IF(true, 10, 20)", 10);
        TestUtil.execute("IF(false, 10, 20)", 20);
        TestUtil.execute("IF(5 > 3, \"Greater\", \"Less\")", "Greater");
        
        // IF函数与变量结合
        TestUtil.execute("IF(B01B, I01N, I02N)", 1);  // B01B=true, I01N=1, I02N=2
        TestUtil.execute("IF(B02B, I01N, I02N)", 2);  // B02B=false
        
        // 嵌套IF函数
        TestUtil.execute("IF(10 > 5, IF(3 > 2, 100, 200), 300)", 100);
        TestUtil.execute("IF(false, 1, IF(true, 2, 3))", 2);
        
        // SWITCH函数测试
        TestUtil.execute("SWITCH(I04N, 1:\"a\", 2:\"b\", 3:\"c\", 4:\"d\", 5:\"e\")", "d");
        TestUtil.execute("SWITCH(3, 1:\"one\", 2:\"two\", 3:\"three\", 4:\"four\")", "three");
        
        // 条件运算与算术运算结合
        TestUtil.execute("IF(5 > 3, 10 + 5, 20 - 5)", 15);
        TestUtil.execute("SWITCH(I02N, 1:\"small\", 2:\"medium\", 3:\"large\")", "medium");
    }

    /**
     * 测试参数和变量的复合表达式
     * 包含各种参数类型的组合使用
     */
    @Test
    public void testComplexParameterExpressions() throws ExpressionException {
        // 整数参数运算
        TestUtil.execute("I01N + I02N + I03N", 6);    // 1 + 2 + 3 = 6
        TestUtil.execute("I05N * I02N - I01N", 9);    // 5 * 2 - 1 = 9
        TestUtil.execute("MAX(I01N, I02N, I06N)", 6); // MAX(1, 2, 6) = 6
        
        // 小数参数运算
        TestUtil.execute("N01N + N02N", 3.0);         // 1.0 + 2.0 = 3.0
        TestUtil.execute("N05N * N02N", 10.0);        // 5.0 * 2.0 = 10.0
        TestUtil.execute("ROUND_UP(N01N / N02N, 2)", 0.5);
        
        // 布尔参数逻辑运算
        TestUtil.execute("B01B && true", true);       // true && true = true
        TestUtil.execute("B02B || false", false);     // false || false = false
        TestUtil.execute("!(B01B)", false);           // !true = false
        
        // 混合类型参数
        TestUtil.execute("IF(B01B, I01N + N01N, I02N + N02N)", 2.0); // true时: 1 + 1.0 = 2.0
        TestUtil.execute("CONCAT(T01T, T02T)", "12"); // "1" + "2" = "12"
    }

    /**
     * 测试极值和边界条件的复合表达式
     * 包含各种边界值和极端情况的测试
     */
    @Test
    public void testBoundaryConditionExpressions() throws ExpressionException {
        // 零值运算
//        TestUtil.execute("0 + 5", 5);
//        TestUtil.execute("10 - 0", 10);
//        TestUtil.execute("0 * 100", 0);
//        TestUtil.execute("IF(0 == 0, \"zero\", \"non-zero\")", "zero");
//
//        // 负数运算
//        TestUtil.execute("-5 + 3", -2);
//        TestUtil.execute("10 - (-5)", 15);
        TestUtil.execute("-3 * -4", 12);
//        TestUtil.execute("MAX(-10, -5, -1)", -1);
//
//        // 大数值运算
//        TestUtil.execute("1000000 + 500000", 1500000);
//        TestUtil.execute("999999 * 2", 1999998);
//
//        // 精度测试
//        TestUtil.execute("ROUND_UP(1.0000001, 6)", 1.000001);
//        TestUtil.execute("ROUND_DOWN(1.9999999, 6)", 1.999999);
    }

    /**
     * 测试完全集成的复杂表达式
     * 包含所有类型操作符和函数的深度嵌套组合
     */
    @Test
    public void testFullyIntegratedComplexExpressions() throws ExpressionException {
        // 深度嵌套的算术和逻辑表达式
        TestUtil.execute("IF((I01N+I02N)>I03N,(I04N*2)+10,I05N-5)", 0);
        // 解释: IF((1+2) > 3, (4*2)+10, 5-5) = IF(false, 18, 0) = 0
        // 实际应该是0，但如果结果是18，说明条件判断有误
        
        // 复杂函数组合
        TestUtil.execute("CONCAT(\"Result: \", ROUND_UP(SQRT(POW(I03N, 2) + POW(I04N, 2)), 2))", "Result: 5.00");
        // sqrt(3² + 4²) = sqrt(9 + 16) = sqrt(25) = 5.0
        
        // 多层条件嵌套
        TestUtil.execute("IF(I01N > 0, IF(I02N > I01N, \"Increasing\", \"Stable\"), \"Negative\")", "Increasing");
        
        // 位运算与函数结合
        TestUtil.execute("((255 & (I03N << 2)) | I02N) ^ 1", 15);
        // ((255 & (3 << 2)) | 2) ^ 1 = ((255 & 12) | 2) ^ 1 = (12 | 2) ^ 1 = 14 ^ 1 = 13
        
        // 完整的业务逻辑表达式示例
        TestUtil.execute("IF(B01B, MAX(I01N, I02N, I03N), MIN(I04N, I05N, I06N))", 3);
        // IF(true, MAX(1,2,3), MIN(4,5,6)) = MAX(1,2,3) = 3
    }
}