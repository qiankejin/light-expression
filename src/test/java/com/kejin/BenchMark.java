package com.kejin;

import com.kejin.compile.GrammarRunner;
import com.kejin.compile.LexicalRunner;
import com.kejin.enums.CompileException;
import com.kejin.enums.ExTreeCache;
import com.kejin.value.Value;
import com.kejin.var.Var;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchMark {
    @Test
    public void 性能测试() throws CompileException {
        //表达式
        String ex = "SWITCH(I04N,1:\"a\",2:\"b\",3:\"c\",4:\"d\",5:\"e\")";
        //编译
        Var compile  = ExpressCompiler.compile(ex);
        //变量入参
        Map<String, Value> varMap = new HashMap<>();
        varMap.put("I04N", Value.of(4));
        //计算表达式
        Value result = compile.fill(varMap);
        System.out.println(compile + "=" + result);
        //触发JIT编译
        for (int i = 0; i < 100000; i++) {
            LexicalRunner.compile(ex);
            compile.fill(varMap);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            compile.fill(varMap);
        }
        System.out.println("计算十万次耗时:" + (System.currentTimeMillis() - begin) + "ms");
        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            ExpressCompiler.compile(ex);
        }
        System.out.println("编译十万次耗时:" + (System.currentTimeMillis() - begin) + "ms");
        begin = System.currentTimeMillis();
        for (long i = 0; i < 100000; i++) {
            LexicalRunner.compile(ex);
        }
        System.out.println("词法分析十万次耗时:" + (System.currentTimeMillis() - begin) + "ms");
        List<Node> lexical=LexicalRunner.compile(ex);
        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            GrammarRunner.grammar(lexical, 0, lexical.size() - 1, false);
        }
        System.out.println("语法分析十万次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
}
