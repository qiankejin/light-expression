package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.calculate.Plus;
import com.kejin.expression.operator.compare.Equal;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import com.kejin.expression.value.Value;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryExpressTest {

    @Test
    public void testBinaryExpressWithDifferentOperators() {
        // 测试BinaryExpress与不同操作符的组合
        Var left = NumberConst.of("10");
        Var right = NumberConst.of("5");
        
        // 测试加法
        Plus plus = new Plus();
        BinaryExpress plusExpr = new BinaryExpress(left, plus, right);
        assertEquals(ValueType.NUMBER, plusExpr.valueType());
        assertEquals(15L, plusExpr.execute(new MapParam()).toLong());
        
        // 测试等于比较
        Equal equal = new Equal();
        BinaryExpress equalExpr = new BinaryExpress(left, equal, left); // 10 == 10
        assertEquals(ValueType.BOOLEAN, equalExpr.valueType());
        assertTrue(equalExpr.execute(new MapParam()).toBoolean());
    }
}