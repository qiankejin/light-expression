package com.kejin.expression.errors;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExecuteException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExecuteExceptionTest {

    @Test
    public void testExecuteExceptionWithDifferentConstructors() {
        // 测试ExecuteException的不同构造函数
        ExecuteException ex1 = new ExecuteException(ErrorCode.GRAMMAR_ERROR);
        assertEquals(80000003, ex1.getCode());
        assertEquals("GRAMMAR_ERROR", ex1.getErrorCode());
        assertEquals("语法错误:[{0}]", ex1.getMessage());

        ExecuteException ex2 = new ExecuteException(ErrorCode.ARG_MISS, "missing argument");
        assertEquals(80000001, ex2.getCode());
        assertTrue(ex2.getMessage().contains("missing argument"));

        ExecuteException ex3 = new ExecuteException(ErrorCode.CALCULATE_ERROR, new RuntimeException("cause"), "calc error");
        assertEquals(80000004, ex3.getCode());
        assertEquals("cause", ex3.getCause().getMessage());
    }
}