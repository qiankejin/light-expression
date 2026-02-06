package com.kejin;

import com.kejin.expression.ExpressCompiler;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;
import org.junit.Assert;

import java.util.Date;

public class TestUtil {

    private static final MapParam argMap = new MapParam();

    static {
        argMap.put("I01N", Value.of(1));
        argMap.put("I02N", Value.of(2));
        argMap.put("I03N", Value.of(3));
        argMap.put("I04N", Value.of(4));
        argMap.put("I05N", Value.of(5));
        argMap.put("I06N", Value.of(6));
        argMap.put("T01T", Value.of("1"));
        argMap.put("T02T", Value.of("2"));
        argMap.put("T03T", Value.of("3"));
        argMap.put("T04T", Value.of("4"));
        argMap.put("T05T", Value.of("5"));
        argMap.put("T06T", Value.of("6"));
        argMap.put("B01B", Value.of(true));
        argMap.put("B02B", Value.of(false));
        argMap.put("N01N", Value.of(1.0));
        argMap.put("N02N", Value.of(2.0));
        argMap.put("N03N", Value.of(3.0));
        argMap.put("N04N", Value.of(4.0));
        argMap.put("N05N", Value.of(5.0));
        argMap.put("N06N", Value.of(6.0));
    }


    private static Value<?> executeSuccess(String expression) {
        try {
            Var compile = ExpressCompiler.compile(expression);
            Value<?> value = compile.execute(argMap);
            return value;
        } catch (ExpressionException e) {
            throw new AssertionError("expected success, but was:<" + e.getErrorCode() + ">", e);
        }
    }

    public static void compileFail(String expression, String errorMessage) {
        try {
            ExpressCompiler.compile(expression);
        } catch (ExpressionException e) {
            // 检查错误消息是否包含预期的错误信息（忽略可能的前缀）
            Assert.assertTrue("Expected error message '" + errorMessage + "' but got '" + e.getMessage() + "'",
                    e.getMessage().contains(errorMessage));
        }
    }

    public static void executeFail(String expression, String errorMessage) {
        try {
            Var compile = ExpressCompiler.compile(expression);
            compile.execute(argMap);
        } catch (ExpressionException e) {
            throw new AssertionError("expected compile success, but was:<" + e.getErrorCode() + ">");
        }
    }


    public static void execute(String expression, int result) {
        Value value = executeSuccess(expression);
        Assert.assertEquals(result, value.toInt());
    }


    public static void execute(String expression, double result) {
        Value value = executeSuccess(expression);
        Assert.assertEquals(result, value.toDouble(), 0);
    }


    public static void execute(String expression, boolean result) {
        Value value = executeSuccess(expression);
        Assert.assertEquals(result, value.toBoolean());
    }

    public static void execute(String expression, Date date){
        Value value = executeSuccess(expression);
        Assert.assertEquals(date.getTime()/100, value.toDate().getTime()/100);
    }


    public static void execute(String expression, String result) {
        Value value = executeSuccess(expression);
        Assert.assertEquals(result, value.toString());
    }

}
