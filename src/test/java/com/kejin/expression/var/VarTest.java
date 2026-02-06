package com.kejin.expression.var;

import com.kejin.expression.enums.ValueType;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.Value;
import org.junit.Test;

import static org.junit.Assert.*;

public class VarTest {

    @Test
    public void testVarImplementations() {
        // 测试各种Var实现的功能
        // 常量测试
        Const numConst = NumberConst.of("123");
        assertEquals(ValueType.NUMBER, numConst.valueType());
        assertEquals(123L, numConst.execute(new MapParam()).toLong());
        
        Const boolConst = BooleanConst.TRUE_CONST;
        assertEquals(ValueType.BOOLEAN, boolConst.valueType());
        assertTrue(boolConst.execute(new MapParam()).toBoolean());
        
        Const textConst = TextConst.of("test");
        assertEquals(ValueType.TEXT, textConst.valueType());
        assertEquals("test", textConst.execute(new MapParam()).toString());
        
        // 参数变量测试
        MapParam params = new MapParam();
        params.put("x", Value.of(10L));
        params.put("y", Value.of(20L));
        
        Arg xArg = NumberArg.of("x");
        assertEquals(ValueType.NUMBER, xArg.valueType());
        assertEquals(10L, xArg.execute(params).toLong());
        
        Arg yArg = NumberArg.of("y");
        assertEquals(20L, yArg.execute(params).toLong());
    }
    
    @Test
    public void testVarEdgeCases() {
        // 测试Var的边缘情况
        try {
            // 尝试将文本转换为数字，应抛出异常
            TextConst text = TextConst.of("hello");
            text.execute(new MapParam()).toLong();
            fail("应该抛出异常");
        } catch (Exception e) {
            // 期望异常
        }
        
        // 测试参数缺失情况
        MapParam params = new MapParam();
        Arg missingArg = NumberArg.of("missing");
        try {
            missingArg.execute(params);
            fail("应该抛出异常");
        } catch (ExecuteException e) {
            assertEquals(80000001, e.getCode()); // ARG_MISS
        }
    }
}