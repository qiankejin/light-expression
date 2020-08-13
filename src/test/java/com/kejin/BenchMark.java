package com.kejin;

import com.kejin.compile.GrammarRunner;
import com.kejin.compile.LexicalRunner;
import com.kejin.enums.CompileException;
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
//      String ex = "TY00027T=\"单进单出\"&&XT00010T<>\"无水阳台\"&&XT00010T<>\"冷水阳台\"&&XT00010T<>\"热水阳台\"&&XT00009T<>\"玄关\"&&XT00009T<>\"过道\"&&XT00009T<>\"门厅\"&&XT00009T<>\"更衣室\"&&XT00009T<>\"干区\"&&XT00009T<>\"淋浴房\"&&XT00009T<>\"储物间\"";
        String ex = "TY00027T==\"单进单出\"&&notIn(XT00010T,\"无水阳台\",\"冷水阳台\",\"热水阳台\")&&notIn(XT00009T,\"玄关\",\"过道\",\"门厅\",\"更衣室\",\"干区\",\"淋浴房\",\"储物间\")";

        //词法分析
        List<Node> lexical = LexicalRunner.compile(ex);
        //语法分析
        Var compile = GrammarRunner.grammar(lexical, 0, lexical.size() - 1, false);
        Map<String, Value> varMap = new HashMap<>();
        varMap.put("XT00005N", Value.of(3));
        varMap.put("XT00023N", Value.of(1));
        varMap.put("TY00020B", Value.of(false));
        varMap.put("XT00025N", Value.of(1));
        System.out.println(compile + "=" + compile.fill(varMap));
        System.out.println(varMap);
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
        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            GrammarRunner.grammar(lexical, 0, lexical.size() - 1, false);
        }
        System.out.println("语法分析十万次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
}
