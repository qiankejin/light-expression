package com.kejin.expression.value;

import com.kejin.expression.enums.ValueType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class ValueTest {

    @Test
    public void testValueConversions() {
        // 测试不同类型Value之间的转换和操作
        Value<?> intVal = Value.of(42L);
        Value<?> decimalVal = Value.of(new BigDecimal("42.5"));
        Value<?> boolVal = Value.of(true);
        Value<?> textVal = Value.of("hello");
        Value<?> dateVal = Value.of(new Date());
        
        assertEquals(42L, intVal.toLong());
        assertEquals(42, intVal.toInt());
        assertEquals(42.0, intVal.toDouble(), 0.001);
        assertEquals(ValueType.NUMBER, intVal.type()); // 使用type()方法
        
        assertTrue(decimalVal.isDouble());
        assertEquals(42.5, decimalVal.toDouble(), 0.001);
        
        assertTrue(boolVal.toBoolean());
        assertFalse(Value.of(false).toBoolean());
        
        assertEquals("hello", textVal.toString());
        assertEquals("hello", textVal.toValue());
    }
    
    @Test
    public void testValueSpecializedClasses() {
        // 测试Value的特定子类
        LongValue numVal = Value.of(42L);
        assertTrue(numVal instanceof LongValue);
        assertEquals(42L, numVal.toLong());
        assertEquals(42, numVal.toInt());
        assertEquals(42.0, numVal.toDouble(), 0.001);
        
        BooleanValue boolVal = Value.of(true);
        assertTrue(boolVal instanceof BooleanValue);
        assertTrue(boolVal.toBoolean());
        
        TextValue textVal = Value.of("test");
        assertTrue(textVal instanceof TextValue);
        assertEquals("test", textVal.toString());
        
        DoubleValue doubleVal = Value.of(new BigDecimal("123.45"));
        assertTrue(doubleVal instanceof DoubleValue);
        assertTrue(doubleVal.isDouble());
        assertEquals(123.45, doubleVal.toDouble(), 0.001);
        
        DateValue dateVal = Value.of(new Date());
        assertTrue(dateVal instanceof DateValue);
        assertNotNull(dateVal.toDate());
    }
}