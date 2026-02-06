package com.kejin.expression.integration;

import com.kejin.TestUtil;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

/**
 * 高级复杂表达式集成测试
 * 包含更复杂的语法组合、嵌套表达式和边缘案例
 */
public class AdvancedComplexExpressionTest {

    /**
     * 测试深度嵌套的算术表达式
     * 验证复杂数学计算的正确性
     */
    @Test
    public void testDeepNestedArithmetic() throws ExpressionException {
        // 多层括号嵌套
        TestUtil.execute("((2+3)*(4-1))+((10/2)*3)", 30); // (5*3)+(5*3) = 15+15 = 30
        TestUtil.execute("(((I01N+I02N)*I03N)-I04N)/I02N", 2); // (((1+2)*3)-4)/2 = ((3*3)-4)/2 = (9-4)/2 = 2.5 -> 2

        // 复杂的运算顺序
        TestUtil.execute("2+3*4-1", 13); // 2+(3*4)-1 = 2+12-1 = 13
        TestUtil.execute("(2+3)*(4-1)", 15); // 5*3 = 15
        TestUtil.execute("2^(3+1)", 6); // 2^4 = 6

        // 混合参数运算
        TestUtil.execute("(I02N+I03N)*(I05N-I03N)", 10); // (2+3)*(5-3) = 5*2 = 10
        TestUtil.execute("I06N/(I03N+I01N)-I02N", 0); // 6/(3+1)-2 = 6/4-2 = 1.5-2 = -0.5 -> 0
    }

    /**
     * 测试复杂的逻辑表达式组合
     * 包含多层逻辑运算和优先级测试
     */
    @Test
    public void testComplexLogicalExpressions() throws ExpressionException {
        // 复杂的AND/OR组合
        TestUtil.execute("true&&(false||true)&&true", true);
        TestUtil.execute("(true||false)&&(false||true)", true);
        TestUtil.execute("!(true&&false)||(true&&true)", true);

        // 与比较运算的深层嵌套
        TestUtil.execute("(5>3)&&(10<15)||false", true);
        TestUtil.execute("((I03N>I01N)&&(I05N<I06N))||(I02N==I02N)", true);

        // 复杂的否定运算
        TestUtil.execute("!((true&&false)||(false&&true))", true);
        TestUtil.execute("!!(true||false)", true);

        // 混合逻辑和算术
        TestUtil.execute("(2+3>4)&&(10/2==5)", true);
        TestUtil.execute("(I01N+I02N>5)||(I03N*I02N<10)", true);
    }

    /**
     * 测试高级函数嵌套和组合
     * 验证复杂函数调用链的正确性
     */
    @Test
    public void testAdvancedFunctionNesting() throws ExpressionException {
        // 多层函数嵌套
        TestUtil.execute("MAX(MIN(10,5),MAX(3,8))", 8);
        TestUtil.execute("ROUND_UP(SQRT(POW(4,2)),2)", 4.0);
        TestUtil.execute("CONCAT(CONCAT(\"Hello\",\" \"),\"World\")", "Hello World");

        // 函数与条件混合
        TestUtil.execute("IF(MAX(1,5,3)>3,\"Large\",\"Small\")", "Large");
        TestUtil.execute("SWITCH(MIN(3,1,5),1:\"One\",3:\"Three\",5:\"Five\")", "One");

        // 复杂数学函数组合
        TestUtil.execute("ABS(-MAX(10,-5,3))", 10);

        // 字符串处理函数
        TestUtil.execute("UPPER(LOWER(\"HeLLo\"))", "HELLO");
        TestUtil.execute("SUB_STR(\"HelloWorld\",1,5)", "elloW");

        // 类型转换函数
        TestUtil.execute("TO_STR(TO_NUM(\"123.45\"))", "123.45");
    }

    /**
     * 测试复杂的条件表达式
     * 包含多层嵌套的IF语句和复杂条件判断
     */
    @Test
    public void testComplexConditionalExpressions() throws ExpressionException {
        // 多层IF嵌套
        TestUtil.execute("IF(true,IF(false,10,20),30)", 20);
        TestUtil.execute("IF(I03N>2,IF(I03N<5,100,200),300)", 100);

        // 复杂的SWITCH语句
        TestUtil.execute("SWITCH(2,1:\"First\",2:\"Second\",3:\"Third\")", "Second");
        TestUtil.execute("SWITCH(I04N,1:\"Low\",2:\"Medium\",3:\"High\",4:\"Very High\")", "Very High");

        // 条件与函数结合
        TestUtil.execute("IF(LEN(\"Hello\")>3,\"Long\",\"Short\")", "Long");
        TestUtil.execute("IF(IS_EMPTY(\"\"),\"Empty\",\"NotEmpty\")", "Empty");

        // 多重条件判断
        TestUtil.execute("IF(I01N>0&&I02N>0&&I03N>0,\"AllPositive\",\"Mixed\")", "AllPositive");
        TestUtil.execute("IF(I05N>=5||I06N>=5,\"HighValue\",\"LowValue\")", "HighValue");
    }

    /**
     * 测试参数变量的复杂使用场景
     * 包含参数间的复杂关系和计算
     */
    @Test
    public void testComplexParameterScenarios() throws ExpressionException {
        // 参数间的复杂数学关系
        TestUtil.execute("(I01N+I02N)*(I03N-I01N)", 6); // (1+2)*(3-1) = 3*2 = 6
        TestUtil.execute("POW(I02N,I03N)+SQRT(I04N)", 10.0); // 2^3 + sqrt(4) = 8 + 2 = 10

        // 参数类型混合运算
        TestUtil.execute("IF(B01B,N01N,N02N)+I03N", 4.0); // true时: 1.0+3 = 4.0
        TestUtil.execute("CONCAT(T01T,CONCAT(\"_\",T02T))", "1_2");

        // 复杂的参数条件判断
        TestUtil.execute("IF(I05N>I03N&&B01B,\"ConditionMet\",\"NotMet\")", "ConditionMet");
        TestUtil.execute("SWITCH(I02N+I03N,4:\"Four\",5:\"Five\",6:\"Six\")", "Five");

        // 参数范围检查
        TestUtil.execute("IF(I04N>=I01N&&I04N<=I06N,\"InRange\",\"OutOfRange\")", "InRange");
    }

    /**
     * 测试边界和异常情况处理
     * 验证表达式引擎在极端情况下的表现
     */
    @Test
    public void testEdgeCasesAndErrorHandling() throws ExpressionException {
        // 极值运算
        TestUtil.execute("MAX(999999,1,0)", 999999);
        TestUtil.execute("MIN(-999999,0,1)", -999999);

        // 精度测试
        TestUtil.execute("ROUND_UP(1.0000001,6)", 1.000001);
        TestUtil.execute("ROUND_DOWN(0.9999999,6)", 0.999999);

        // 绝对值
        TestUtil.execute("ABS(0)", 0);

    }

    /**
     * 测试实际业务场景的复杂表达式
     * 模拟真实世界的应用场景
     */
    @Test
    public void testRealWorldBusinessScenarios() throws ExpressionException {
        // 薪资计算公式
        TestUtil.execute("IF(I05N>=5,(I04N*1.2)+1000,I04N*1000)", 1004.8); // 假设基础薪资4000

        // 绩效等级判定
        TestUtil.execute("SWITCH(IF(I06N>=5,\"A\",IF(I06N>=3,\"B\",\"C\")),\"A\":\"Excellent\",\"B\":\"Good\",\"C\":\"Average\")", "Excellent");

        // 库存管理逻辑
        TestUtil.execute("IF(I03N>0,\"InStock\",IF(I03N==0,\"OutOfStock\",\"BackOrder\"))", "InStock");

        // 客户分级系统
        TestUtil.execute("CONCAT(\"Grade_\",IF(I05N>=5,\"VIP\",IF(I05N>=3,\"Premium\",\"Regular\")))", "Grade_VIP");

        // 复合计费逻辑
        TestUtil.execute("IF(I06N>0,I06N*10+IF(I06N>5,50,0),0)", 110); // 6*10+50 = 110

        // 数据验证规则
        TestUtil.execute("IF(I01N>=1&&I01N<=10&&I02N>=1&&I02N<=10,\"Valid\",\"Invalid\")", "Valid");
    }

    /**
     * 测试性能相关的复杂表达式
     * 验证表达式引擎处理大量计算的能力
     */
    @Test
    public void testPerformanceRelatedExpressions() throws ExpressionException {
        // 循环计算模拟
        TestUtil.execute("((I01N+I02N+I03N+I04N+I05N+I06N)/6)", 3); // 平均值计算

        // 累积计算
        TestUtil.execute("I01N+I02N+I03N+I04N+I05N+I06N", 21); // 1+2+3+4+5+6 = 21

        // 复杂的统计函数
        TestUtil.execute("MAX(I01N,I02N,I03N,I04N,I05N,I06N)-MIN(I01N,I02N,I03N,I04N,I05N,I06N)", 5); // 6-1 = 5

        // 分组计算
        TestUtil.execute("IF(I01N%2==0,\"Even_Group\",\"Odd_Group\")", "Odd_Group");
        TestUtil.execute("IF(I02N%2==0,\"Even_Group\",\"Odd_Group\")", "Even_Group");
    }

    /**
     * 测试国际化和特殊字符处理
     * 验证表达式引擎对不同字符集的支持
     */
    @Test
    public void testInternationalizationSupport() throws ExpressionException {
        // 基本字符串处理
        TestUtil.execute("CONCAT(\"中文\",\"测试\")", "中文测试");
        TestUtil.execute("CONCAT(\"Hello\",\"世界\")", "Hello世界");

        // 特殊字符处理
        TestUtil.execute("LEN(\"@#$%^&*\")", 7);
        TestUtil.execute("CONCAT(\"Line1\",\"\n\",\"Line2\")", "Line1\nLine2");

        // Unicode字符支持
        TestUtil.execute("UPPER(\"café\")", "CAFÉ");
        TestUtil.execute("LEN(\"🙂🎉🚀\")", 3);
    }
}