package com.kejin.expression.express;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.BooleanConst;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.TextConst;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaseExpressTest {

    @Test
    public void testCaseExpressFunctionality() {
        // 测试CaseExpress功能
        Var condition = BooleanConst.TRUE_CONST;
        Var result = NumberConst.of("42");
        
        CaseExpress caseExpr = new CaseExpress(condition, result);
        assertEquals(ValueType.NUMBER, caseExpr.valueType());
        assertEquals(42L, caseExpr.getThenVar().execute(new MapParam()).toLong());
        assertFalse(caseExpr.isDefault());
        
        // 测试默认case
        Var defaultCond = TextConst.of("default");
        CaseExpress defaultCase = new CaseExpress(defaultCond, result);
        assertTrue(defaultCase.isDefault());
    }
}