package com.kejin.expression.util;

import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionCacheTest {

    @Test
    public void testExpressionCacheFunctionality() {
        // 测试表达式缓存功能
        ExpressionCache cache = new ExpressionCache();
        
        // 测试缓存获取
        Var result1 = cache.getWithInit("key1", k -> NumberConst.of("123"), Var.class);
        Var result2 = cache.getWithInit("key1", k -> NumberConst.of("456"), Var.class);
        
        // 应该返回相同的对象（从缓存）
        assertEquals(result1.execute(new MapParam()).toLong(), result2.execute(new MapParam()).toLong());
    }
}