package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongValueTest {

    @Test
    public void testIntegerValueFunctionality() {
        // 直接创建IntegerValue
        LongValue longValue = Value.of(42);
        assertEquals(42, longValue.toInt());
        assertEquals(42L, longValue.toLong());
        assertEquals(42.0, longValue.toDouble(), 0.001);
        assertEquals("42", longValue.toString());
        assertEquals(ValueType.NUMBER, longValue.type());
        assertEquals(42, longValue.toMath().round());  // 比较toMath()的结果，使用round()方法
        assertEquals(42L, longValue.toValue());

        // 测试IntegerValue的eq方法
        LongValue otherIntValue = Value.of(42);
        assertTrue(longValue.eq(otherIntValue));

        // 测试compareTo方法
        LongValue smallerValue = Value.of(20);
        assertTrue(longValue.compareTo(smallerValue) > 0);
        assertTrue(smallerValue.compareTo(longValue) < 0);
        assertEquals(0, longValue.compareTo(otherIntValue));
    }
}
