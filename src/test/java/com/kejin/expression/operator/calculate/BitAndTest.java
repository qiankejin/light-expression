package com.kejin.expression.operator.calculate;

import com.kejin.TestUtil;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.operator.CalculateOperator;
import com.kejin.expression.value.LongValue;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitAndTest {

    @Test
    public void testBitAndOperatorBasicFunctionality() {
        BitAnd bitAnd = new BitAnd();
        
        // 测试基本属性
        assertEquals("&", bitAnd.symbol());
        assertEquals(7, bitAnd.priority()); // LEVEL_7
        
        // 测试返回类型
        Var left = NumberConst.of("5");
        Var right = NumberConst.of("3");
        assertEquals(ValueType.NUMBER, bitAnd.returnType(left, right));
    }

    @Test
    public void testBitAndOperatorCalculation() {
        BitAnd bitAnd = new BitAnd();
        
        // 测试基本位与运算
        // 5 (101) & 3 (011) = 1 (001)
        Value<?> result1 = bitAnd.operatorCalculate(Value.of(5L), Value.of(3L));
        assertEquals(1L, result1.toLong());
        
        // 15 (1111) & 7 (0111) = 7 (0111)
        Value<?> result2 = bitAnd.operatorCalculate(Value.of(15L), Value.of(7L));
        assertEquals(7L, result2.toLong());
        
        // 8 (1000) & 4 (0100) = 0 (0000)
        Value<?> result3 = bitAnd.operatorCalculate(Value.of(8L), Value.of(4L));
        assertEquals(0L, result3.toLong());
        
        // 255 (11111111) & 15 (00001111) = 15 (00001111)
        Value<?> result4 = bitAnd.operatorCalculate(Value.of(255L), Value.of(15L));
        assertEquals(15L, result4.toLong());
    }

    @Test
    public void testBitAndWithZeroAndNegativeNumbers() {
        BitAnd bitAnd = new BitAnd();
        
        // 任何数与0进行位与运算都等于0
        Value<?> result1 = bitAnd.operatorCalculate(Value.of(42L), Value.of(0L));
        assertEquals(0L, result1.toLong());
        
        Value<?> result2 = bitAnd.operatorCalculate(Value.of(0L), Value.of(100L));
        assertEquals(0L, result2.toLong());
        
        // 0 & 0 = 0
        Value<?> result3 = bitAnd.operatorCalculate(Value.of(0L), Value.of(0L));
        assertEquals(0L, result3.toLong());
        
        // 测试负数（在Java中使用补码表示）
        // -1的二进制表示全为1，与任何正数进行位与运算等于该正数本身
        Value<?> result4 = bitAnd.operatorCalculate(Value.of(-1L), Value.of(5L));
        assertEquals(5L, result4.toLong());
    }

    @Test
    public void testBitAndWithSameNumbers() {
        BitAnd bitAnd = new BitAnd();
        
        // 相同数字进行位与运算等于该数字本身
        Value<?> result1 = bitAnd.operatorCalculate(Value.of(42L), Value.of(42L));
        assertEquals(42L, result1.toLong());
        
        Value<?> result2 = bitAnd.operatorCalculate(Value.of(255L), Value.of(255L));
        assertEquals(255L, result2.toLong());
        
        Value<?> result3 = bitAnd.operatorCalculate(Value.of(1L), Value.of(1L));
        assertEquals(1L, result3.toLong());
    }

    @Test
    public void testBitAndOperatorPrecedence() {
        // 测试运算符优先级
        // & 的优先级是 7，比 | (9) 高，但比算术运算符 (2,3) 低
        BitAnd bitAnd = new BitAnd();
        assertEquals(7, bitAnd.priority());
    }

    @Test
    public void testBitAndWithExpression() {
        // 测试实际表达式求值
        TestUtil.execute("5&3", 1L);
        TestUtil.execute("15&7", 7L);
        TestUtil.execute("8&4", 0L);
        TestUtil.execute("255&15", 15L);
    }

    @Test
    public void testBitAndOperatorInheritance() {
        BitAnd bitAnd = new BitAnd();
        assertTrue(bitAnd instanceof CalculateOperator);
    }

    @Test
    public void testBitAndWithLargeNumbers() {
        BitAnd bitAnd = new BitAnd();
        
        // 测试大数位与运算
        Value<?> result1 = bitAnd.operatorCalculate(Value.of(1000000L), Value.of(999999L));
        assertEquals(999936L, result1.toLong()); // 1000000 & 999999 = 999936
        
        // 测试边界值
        Value<?> result2 = bitAnd.operatorCalculate(Value.of(Long.MAX_VALUE), Value.of(1L));
        assertEquals(1L, result2.toLong());
    }

    @Test
    public void testBitAndOperatorSymbol() {
        BitAnd bitAnd = new BitAnd();
        assertEquals("&", bitAnd.symbol());
    }
}