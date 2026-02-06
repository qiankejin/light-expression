package com.kejin.expression.express;

import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.var.NumberArg;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;
import com.kejin.expression.value.Value;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CommaExpressTest {

    @Test
    public void testCommaExpressFunctionality() {
        // 测试CommaExpress功能
        Var arg1 = NumberConst.of("1");
        Var arg2 = NumberConst.of("2");
        Var arg3 = NumberConst.of("3");

        CommaExpress commaExpr = new CommaExpress(Arrays.asList(arg1, arg2, arg3));

        assertEquals(Lexical.ARG_LIST, commaExpr.lexical());
        assertEquals(ValueType.NUMBER, commaExpr.valueType());

        // 测试execute方法 - 返回第一个参数的值
        Value<?> result = commaExpr.execute(new MapParam());
        assertEquals(3L, result.toLong());

        // 测试toString方法
        String str = commaExpr.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("2"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains(","));

        // 测试fillArgList方法
        List<Value<?>> filledArgs = commaExpr.fillArgList(new MapParam());
        assertEquals(3, filledArgs.size());
        assertEquals(1L, filledArgs.get(0).toLong());
        assertEquals(2L, filledArgs.get(1).toLong());
        assertEquals(3L, filledArgs.get(2).toLong());

        // 测试usedArg方法
        Set<String> args = new HashSet<>();
        commaExpr.usedArg(args);
        // 这里应该不会添加任何参数，因为都是常量
        assertTrue(args.isEmpty());
    }
}