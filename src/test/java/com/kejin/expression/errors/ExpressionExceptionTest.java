package com.kejin.expression.errors;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExpressionException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionExceptionTest {

    @Test
    public void testExpressionExceptionWithDifferentConstructors() {
        // 测试ExpressionException的不同构造函数
        ExpressionException ex1 = new ExpressionException(ErrorCode.GRAMMAR_ERROR);
        assertEquals(80000003, ex1.getCode());
        assertEquals("GRAMMAR_ERROR", ex1.getErrorCode());

        ExpressionException ex2 = new ExpressionException(ErrorCode.ARG_MISS, "missing argument");
        assertEquals(80000001, ex2.getCode());
        assertTrue(ex2.getMessage().contains("missing argument"));
    }
}