package com.kejin;

import com.kejin.enums.CompileException;
import com.kejin.value.ErrorValue;
import com.kejin.value.Value;
import com.kejin.var.Var;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    private static final Map<String, Value> argMap = new HashMap<>();

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
        argMap.put("D01N", Value.of(1.0));
        argMap.put("D02N", Value.of(2.0));
        argMap.put("D03N", Value.of(3.0));
        argMap.put("D04N", Value.of(4.0));
        argMap.put("D05N", Value.of(5.0));
        argMap.put("D06N", Value.of(6.0));
    }


    private static Value executeSuccess(String expression) {
        try {
            Var compile = ExpressCompiler.compile(expression);
            Value value = compile.fill(argMap);
            if (value.isSuccess()) {
                return value;
            } else {
                throw new AssertionError("expected success, but was:<" + value + ">");
            }
        } catch (CompileException e) {
            throw new AssertionError("expected success, but was:<" + e.getError() + ">",e);
        }
    }

    public static void compileFail(String expression, String errorMessage) {
        try {
            ExpressCompiler.compile(expression);
        } catch (CompileException e) {
            Assert.assertEquals(errorMessage, e.getError());
        }
    }

    public static void executeFail(String expression, String errorMessage) {
        try {
            Var compile = ExpressCompiler.compile(expression);
            Value value = compile.fill(argMap);
            if (value.isSuccess()) {
                throw new AssertionError("expected execute error, but was success");
            }
            Assert.assertEquals(errorMessage, ((ErrorValue) value).getErrorMessage());
        } catch (CompileException e) {
            throw new AssertionError("expected compile success, but was:<" + e.getError() + ">");
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


    public static void execute(String expression, String result) {
        Value value = executeSuccess(expression);
        Assert.assertEquals(result, value.toString());
    }

}
