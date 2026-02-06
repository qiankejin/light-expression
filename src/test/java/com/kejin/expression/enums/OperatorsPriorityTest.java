package com.kejin.expression.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperatorsPriorityTest {

    @Test
    public void testOperatorsPriorityLevels() {
        // 测试操作符优先级
        assertEquals(0, OperatorsPriority.LEVEL_0);
        assertEquals(1, OperatorsPriority.LEVEL_1);
        assertEquals(2, OperatorsPriority.LEVEL_2);
        assertEquals(3, OperatorsPriority.LEVEL_3);  // 加法
        assertEquals(6, OperatorsPriority.LEVEL_6);  // 等于
        assertEquals(9, OperatorsPriority.LEVEL_9);  // 或运算
        assertEquals(12, OperatorsPriority.LEVEL_12); // 赋值
    }
}
