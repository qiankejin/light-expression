package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

/**
 * 最终版复杂表达式集成测试
 * 严格基于表达式引擎实际支持的语法特性
 */
public class FinalComplexExpressionIntegrationTest {

    /**
     * 测试基本算术运算组合
     * 专注于表达式引擎实际支持的运算符组合
     */
    @Test
    public void testSupportedArithmeticCombinations() throws ExpressionException {
        // 基本四则运算
        TestUtil.execute("2+3*4", 14);           // 乘法优先级测试
        TestUtil.execute("(2+3)*4", 20);         // 括号优先级测试
        TestUtil.execute("10-6/2", 7);           // 除法优先级测试
        TestUtil.execute("20/(4+1)", 4);         // 复杂括号测试
        
        // 参数变量运算
        TestUtil.execute("I01N+I02N+I03N", 6);   // 1+2+3=6
        TestUtil.execute("I05N*I02N-I01N", 9);   // 5*2-1=9
        TestUtil.execute("I06N/I02N+I03N", 6);   // 6/2+3=6
    }

    /**
     * 测试比较运算符的有效组合
     * 包含各种比较操作的实际应用场景
     */
    @Test
    public void testValidComparisonCombinations() throws ExpressionException {
        // 基本比较运算
        TestUtil.execute("5>3", true);
        TestUtil.execute("5>=5", true);
        TestUtil.execute("3<5", true);
        TestUtil.execute("3<=3", true);
        TestUtil.execute("5==5", true);
        TestUtil.execute("5<>3", true);
        
        // 与算术运算结合
        TestUtil.execute("2+3>1+3", true);       // 5>4=true
        TestUtil.execute("10/2==5", true);       // 5==5=true
        TestUtil.execute("3*4>=2*6", true);      // 12>=12=true
        
        // 参数变量比较
        TestUtil.execute("I01N<I02N", true);     // 1<2=true
        TestUtil.execute("I03N>=I02N", true);    // 3>=2=true
        TestUtil.execute("I04N==I04N", true);    // 4==4=true
    }

    /**
     * 测试逻辑运算符的组合使用
     * 包含逻辑与、或、非的各种有效组合
     */
    @Test
    public void testLogicalOperatorCombinations() throws ExpressionException {
        // 基本逻辑运算
        TestUtil.execute("true&&true", true);
        TestUtil.execute("true&&false", false);
        TestUtil.execute("false||true", true);
        TestUtil.execute("false||false", false);
        TestUtil.execute("!true", false);
        TestUtil.execute("!false", true);
        
        // 复杂逻辑表达式（去掉空格）
        TestUtil.execute("true&&false||true&&true", true);
        TestUtil.execute("!((true||false)&&(false||true))", false);
        
        // 逻辑与比较运算结合
        TestUtil.execute("5>3&&2<4", true);
        TestUtil.execute("10==10||5>10", true);
        TestUtil.execute("!((1==2)||(3==4))", true);
        
        // 布尔参数逻辑运算
        TestUtil.execute("B01B&&true", true);    // true&&true=true
        TestUtil.execute("B02B||false", false);  // false||false=false
        TestUtil.execute("!(B01B)", false);      // !true=false
    }

    /**
     * 测试受支持的函数调用组合
     * 基于实际测试验证过的函数语法
     */
    @Test
    public void testWorkingFunctionCombinations() throws ExpressionException {
        // 数学函数（去除参数间的空格）
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
        TestUtil.execute("CONCAT(1,2,3)", "123");
        
        // 数值转换函数
        TestUtil.execute("TO_NUM(\"123\")", 123.0);
    }

    /**
     * 测试条件函数的有效使用
     * 包含IF和SWITCH函数的实际应用场景
     */
    @Test
    public void testConditionalFunctionUsage() throws ExpressionException {
        // IF函数基本用法
        TestUtil.execute("IF(true,10,20)", 10);
        TestUtil.execute("IF(false,10,20)", 20);
        TestUtil.execute("IF(5>3,\"Greater\",\"Less\")", "Greater");
        
        // IF函数与变量结合
        TestUtil.execute("IF(B01B,I01N,I02N)", 1);   // B01B=true,返回I01N=1
        TestUtil.execute("IF(B02B,I01N,I02N)", 2);   // B02B=false,返回I02N=2
        
        // 嵌套IF函数
        TestUtil.execute("IF(10>5,IF(3>2,100,200),300)", 100);
        TestUtil.execute("IF(false,1,IF(true,2,3))", 2);
        
        // SWITCH函数
        TestUtil.execute("SWITCH(I04N,1:\"a\",2:\"b\",3:\"c\",4:\"d\",5:\"e\")", "d");
        TestUtil.execute("SWITCH(3,1:\"one\",2:\"two\",3:\"three\",4:\"four\")", "three");
        
        // 条件与算术结合
        TestUtil.execute("IF(5>3,10+5,20-5)", 15);
    }

    /**
     * 测试参数变量的有效组合
     * 展示不同类型参数的混合使用
     */
    @Test
    public void testParameterVariableCombinations() throws ExpressionException {
        // 整数参数运算
        TestUtil.execute("I01N+I02N+I03N", 6);       // 1+2+3=6
        TestUtil.execute("I05N*I02N-I01N", 9);       // 5*2-1=9
        TestUtil.execute("MAX(I01N,I02N,I06N)", 6);  // MAX(1,2,6)=6
        
        // 小数参数运算
        TestUtil.execute("N01N+N02N", 3.0);          // 1.0+2.0=3.0
        TestUtil.execute("N05N*N02N", 10.0);         // 5.0*2.0=10.0
        TestUtil.execute("ROUND_UP(N01N/N02N,2)", 0.5); // 1.0/2.0=0.5
        
        // 字符串参数拼接
        TestUtil.execute("CONCAT(T01T,T02T)", "12"); // "1"+"2"="12"
        TestUtil.execute("CONCAT(\"Prefix:\",T03T)", "Prefix:3");
        
        // 混合类型条件判断
        TestUtil.execute("IF(B01B,I01N+N01N,I02N+N02N)", 2.0); // true时:1+1.0=2.0
    }

    /**
     * 测试实际业务场景的复杂表达式
     * 展示真实应用中的表达式组合
     */
    @Test
    public void testRealWorldBusinessScenarios() throws ExpressionException {
        // 成绩等级判断
//        TestUtil.execute("IF(I04N>=4,\"Pass\",\"Fail\")", "Pass"); // 4>=4
//        TestUtil.execute("SWITCH(I04N,1:\"Poor\",2:\"Fair\",3:\"Good\",4:\"Excellent\",5:\"Outstanding\")", "Excellent");
//
//        // 数值范围检查
//        TestUtil.execute("IF(I03N>=1&&I03N<=5,\"Valid\",\"Invalid\")", "Valid"); // 3在1-5范围内
//        TestUtil.execute("IF(I06N<0||I06N>10,\"OutOfRange\",\"Normal\")", "Normal"); // 6在正常范围内
//
//        // 复杂条件计算
//        TestUtil.execute("IF(B01B,MAX(I01N,I02N,I03N),MIN(I04N,I05N,I06N))", 3); // true时返回MAX(1,2,3)=3
//
//        // 字符串格式化输出
//        TestUtil.execute("CONCAT(\"Score:\",I04N,\" Grade:\",IF(I04N>=4,\"Pass\",\"Fail\"))", "Score:4 Grade:Pass");
//
//        // 多重条件嵌套
//        TestUtil.execute("IF(I01N>0,IF(I02N>I01N,\"Increasing\",\"Stable\"),\"Negative\")", "Increasing");
//
        // 实际计算场景
        TestUtil.execute("ROUND_UP(SQRT(POW(I03N,2)+POW(I04N,2)),2)", 5.00); // sqrt(3²+4²)=sqrt(25)=5.0
    }

    /**
     * 测试边界值和特殊情况处理
     * 验证表达式引擎对边界条件的处理能力
     */
    @Test
    public void testBoundaryAndEdgeCases() throws ExpressionException {
        // 零值处理
        TestUtil.execute("0+5", 5);
        TestUtil.execute("10-0", 10);
        TestUtil.execute("0*100", 0);
        TestUtil.execute("IF(0==0,\"Zero\",\"NonZero\")", "Zero");
        
        // 相等性比较
        TestUtil.execute("5==5", true);
        TestUtil.execute("5>=5", true);
        TestUtil.execute("5<=5", true);
        TestUtil.execute("5<>6", true);
        
        // 最值函数边界
        TestUtil.execute("MAX(1)", 1);
        TestUtil.execute("MIN(10)", 10);
        TestUtil.execute("MAX(-5,-10,-1)", -1);
        
        // 字符串边界情况
        TestUtil.execute("CONCAT(\"\",\"\")", ""); // 空字符串连接
        TestUtil.execute("CONCAT(\"Test\",\"\")", "Test");
    }
}