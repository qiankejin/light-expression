package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

/**
 * 保守版复杂表达式集成测试
 * 严格基于已验证的工作语法特性
 */
public class ConservativeComplexExpressionTest {

    /**
     * 测试最基本的算术表达式组合
     * 这些都是经过验证的最基本语法
     */
    @Test
    public void testBasicArithmetic() throws ExpressionException {
        // 基本运算
        TestUtil.execute("2+3", 5);
        TestUtil.execute("10-4", 6);
        TestUtil.execute("3*4", 12);
        TestUtil.execute("15/3", 5);
        
        // 带括号的运算
        TestUtil.execute("(2+3)*4", 20);
        TestUtil.execute("2*(3+4)", 14);
        TestUtil.execute("(10-6)/2", 2);
        
        // 参数变量运算
        TestUtil.execute("I01N+I02N", 3);    // 1+2=3
        TestUtil.execute("I03N*I02N", 6);    // 3*2=6
        TestUtil.execute("I06N/I03N", 2);    // 6/3=2
    }

    /**
     * 测试比较运算符的基本用法
     * 包含所有支持的比较操作
     */
    @Test
    public void testComparisonOperators() throws ExpressionException {
        // 基本比较
        TestUtil.execute("5>3", true);
        TestUtil.execute("3>5", false);
        TestUtil.execute("5>=5", true);
        TestUtil.execute("5>=6", false);
        TestUtil.execute("3<5", true);
        TestUtil.execute("5<3", false);
        TestUtil.execute("3<=3", true);
        TestUtil.execute("3<=2", false);
        TestUtil.execute("5==5", true);
        TestUtil.execute("5==3", false);
        TestUtil.execute("5<>3", true);
        TestUtil.execute("5<>5", false);
        
        // 与算术结合
        TestUtil.execute("2+3>4", true);     // 5>4
        TestUtil.execute("10/2==5", true);   // 5==5
        TestUtil.execute("3*2>=5", true);    // 6>=5
        
        // 参数变量比较
        TestUtil.execute("I01N<I02N", true);     // 1<2
        TestUtil.execute("I03N>=I02N", true);    // 3>=2
        TestUtil.execute("I04N==I04N", true);    // 4==4
    }

    /**
     * 测试逻辑运算符的基本组合
     * 使用已知工作的语法格式
     */
    @Test
    public void testLogicalOperators() throws ExpressionException {
        // 基本逻辑运算
        TestUtil.execute("true&&true", true);
        TestUtil.execute("true&&false", false);
        TestUtil.execute("false&&true", false);
        TestUtil.execute("false&&false", false);
        
        TestUtil.execute("true||true", true);
        TestUtil.execute("true||false", true);
        TestUtil.execute("false||true", true);
        TestUtil.execute("false||false", false);
        
        TestUtil.execute("!true", false);
        TestUtil.execute("!false", true);
        
        // 简单组合（不带空格）
        TestUtil.execute("true&&false||true&&true", true);
        
        // 与比较运算结合
        TestUtil.execute("5>3&&2<4", true);
        TestUtil.execute("10==10||5>10", true);
        
        // 布尔参数
        TestUtil.execute("B01B&&true", true);    // true&&true
        TestUtil.execute("B02B||false", false);  // false||false
    }

    /**
     * 测试核心函数的基本调用
     * 只包含经过验证的函数语法
     */
    @Test
    public void testCoreFunctions() throws ExpressionException {
        // 数学函数
        TestUtil.execute("MAX(1,5,3)", 5);
        TestUtil.execute("MIN(10,5,15)", 5);
        TestUtil.execute("POW(2,3)", 8);
        
        // 三角函数
        TestUtil.execute("ROUND_UP(SIN(0),4)", 0.0);
        TestUtil.execute("ROUND_UP(COS(0),4)", 1.0);
        
        // 字符串函数
        TestUtil.execute("CONCAT(\"Hello\",\"World\")", "HelloWorld");
        TestUtil.execute("CONCAT(1,2,3)", "123");
        
        // 数值转换
        TestUtil.execute("TO_NUM(\"123\")", 123.0);
        
        // 条件函数
        TestUtil.execute("IF(true,10,20)", 10);
        TestUtil.execute("IF(false,10,20)", 20);
        TestUtil.execute("SWITCH(3,1:\"a\",2:\"b\",3:\"c\")", "c");
    }

    /**
     * 测试参数变量的有效使用
     * 展示不同类型参数的基本操作
     */
    @Test
    public void testParameterUsage() throws ExpressionException {
        // 整数参数
        TestUtil.execute("I01N+I02N+I03N", 6);   // 1+2+3=6
        TestUtil.execute("I05N*I02N", 10);       // 5*2=10
        TestUtil.execute("I06N-I01N", 5);        // 6-1=5
        
        // 小数参数
        TestUtil.execute("N01N+N02N", 3.0);      // 1.0+2.0=3.0
        TestUtil.execute("N03N*N02N", 6.0);      // 3.0*2.0=6.0
        
        // 布尔参数
        TestUtil.execute("B01B", true);
        TestUtil.execute("B02B", false);
        
        // 字符串参数
        TestUtil.execute("CONCAT(T01T,T02T)", "12"); // "1"+"2"
        
        // 混合使用
        TestUtil.execute("IF(B01B,I01N,I02N)", 1);   // true时返回1
        TestUtil.execute("IF(B02B,I01N,I02N)", 2);   // false时返回2
    }

    /**
     * 测试实际可用的简单组合表达式
     * 基于已验证的语法构建的复合表达式
     */
    @Test
    public void testSimpleCombinations() throws ExpressionException {
        // 算术与条件结合
        TestUtil.execute("IF(I03N>2,I03N*2,I03N)", 6); // 3>2为true，返回3*2=6
        TestUtil.execute("IF(I01N==1,\"First\",\"Other\")", "First");
        
        // 函数嵌套
        TestUtil.execute("ROUND_UP(SQRT(16),2)", 4.0);
        TestUtil.execute("MAX(MIN(10,5),3)", 5);
        
        // 多层条件
        TestUtil.execute("IF(I01N>0,IF(I02N>I01N,100,200),300)", 100);
        
        // 实际应用示例
        TestUtil.execute("IF(I04N>=4,\"Pass\",\"Fail\")", "Pass"); // 4>=4
        TestUtil.execute("CONCAT(\"Score:\",I04N)", "Score:4");
    }

    /**
     * 测试边界和特殊情况
     * 验证表达式引擎的基本边界处理
     */
    @Test
    public void testBoundaryCases() throws ExpressionException {
        // 零值处理
        TestUtil.execute("0+5", 5);
        TestUtil.execute("10-0", 10);
        TestUtil.execute("0*100", 0);
        TestUtil.execute("5*0", 0);
        
        // 相等性测试
        TestUtil.execute("5==5", true);
        TestUtil.execute("5>=5", true);
        TestUtil.execute("5<=5", true);
        TestUtil.execute("5<>6", true);
        
        // 简单条件
        TestUtil.execute("IF(5>3,1,0)", 1);
        TestUtil.execute("IF(3>5,1,0)", 0);
        
        // 字符串边界
        TestUtil.execute("CONCAT(\"\",\"\")", "");
        TestUtil.execute("CONCAT(\"Test\",\"\")", "Test");
    }

    /**
     * 测试完整的业务逻辑表达式
     * 展示实际应用中的典型表达式组合
     */
    @Test
    public void testBusinessLogicExpressions() throws ExpressionException {
        // 成绩评定系统
        TestUtil.execute("IF(I05N>=5,\"Excellent\",IF(I05N>=3,\"Good\",\"Needs Improvement\"))", "Excellent");
        
        // 数值范围检查
        TestUtil.execute("IF(I03N>=1&&I03N<=5,\"Valid Range\",\"Out of Range\")", "Valid Range");
        
        // 复杂条件判断
        TestUtil.execute("IF(B01B&&I02N>1,\"Condition Met\",\"Condition Not Met\")", "Condition Met");
        
        // 数据聚合计算
        TestUtil.execute("MAX(I01N,I02N,I03N,I04N)", 4);
        TestUtil.execute("MIN(I03N,I04N,I05N)", 3);
        
        // 格式化输出
        TestUtil.execute("CONCAT(\"Result: \",ROUND_UP(SQRT(25),2))", "Result: 5.00");
        
        // 实际业务规则
        TestUtil.execute("IF(I06N%2==0,\"Even\",\"Odd\")", "Even"); // 6是偶数
        TestUtil.execute("SWITCH(I02N,1:\"Low\",2:\"Medium\",3:\"High\")", "Medium");
    }
}