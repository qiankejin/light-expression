package com.kejin;

import com.kejin.expression.ExpressCompiler;
import com.kejin.expression.Node;
import com.kejin.expression.compile.GrammarRunner;
import com.kejin.expression.compile.LexicalRunner;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.param.MapParam;
import com.kejin.expression.value.Value;
import com.kejin.expression.var.Var;
import org.junit.Test;

import java.util.*;

public class BenchMark {

    private static final int WARMUP_ITERATIONS = 300000;
    private static final int TEST_ITERATIONS = 100000;
    @Test
    public void 性能测试() throws ExpressionException {
        //表达式
        String ex = "SWITCH(I04N,1:\"a\",2:\"b\",3:\"c\",4:\"d\",5:\"e\")";
        //编译
        Var compile = ExpressCompiler.compile(ex);
        //变量入参
        MapParam mapParam = new MapParam();

        mapParam.put("I04N", 4);
        //计算表达式
        Value<?> result = compile.execute(mapParam);
        Set<String> usedArg = new HashSet<>();
        compile.usedArg(usedArg);
        System.out.println(usedArg);

        System.out.println(compile + "=" + result);
        
        //预热阶段
        warmupPhase(ex, compile, mapParam);
        
        //执行性能测试
        executePerformanceTests(ex, compile, mapParam);
    }
    
    private void warmupPhase(String ex, Var compile, MapParam mapParam) throws ExpressionException {
        System.out.println("开始预热...");
        try {
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                LexicalRunner.compile(ex);
                compile.execute(mapParam);
            }
        } catch (ExpressionException e) {
            System.err.println("预热阶段发生异常: " + e.getMessage());
            throw e;
        }
        System.out.println("预热完成");
    }
    
    private void executePerformanceTests(String ex, Var compile, MapParam mapParam) throws ExpressionException {
        //计算性能测试
        testExecutionPerformance(compile, mapParam);
        
        //编译性能测试
        testCompilationPerformance(ex);
        
        //词法分析性能测试
        testLexicalAnalysisPerformance(ex);
        
        //语法分析性能测试
        testGrammarAnalysisPerformance(ex);
    }
    
    private void testExecutionPerformance(Var compile, MapParam mapParam) throws ExpressionException {
        System.out.println("开始计算性能测试...");
        long begin = System.currentTimeMillis();
        try {
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                compile.execute(mapParam);
            }
        } catch (ExpressionException e) {
            System.err.println("计算性能测试发生异常: " + e.getMessage());
            throw e;
        }
        System.out.println("计算" + TEST_ITERATIONS + "次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
    
    private void testCompilationPerformance(String ex) throws ExpressionException {
        System.out.println("开始编译性能测试...");
        long begin = System.currentTimeMillis();
        try {
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                ExpressCompiler.compile(ex);
            }
        } catch (ExpressionException e) {
            System.err.println("编译性能测试发生异常: " + e.getMessage());
            throw e;
        }
        System.out.println("编译" + TEST_ITERATIONS + "次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
    
    private void testLexicalAnalysisPerformance(String ex) throws ExpressionException {
        System.out.println("开始词法分析性能测试...");
        long begin = System.currentTimeMillis();
        try {
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                LexicalRunner.compile(ex);
            }
        } catch (ExpressionException e) {
            System.err.println("词法分析性能测试发生异常: " + e.getMessage());
            throw e;
        }
        System.out.println("词法分析" + TEST_ITERATIONS + "次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
    
    private void testGrammarAnalysisPerformance(String ex) throws ExpressionException {
        System.out.println("开始语法分析性能测试...");
        List<Node> lexical;
        try {
            lexical = LexicalRunner.compile(ex);
        } catch (ExpressionException e) {
            System.err.println("语法分析准备阶段发生异常: " + e.getMessage());
            throw e;
        }
        
        long begin = System.currentTimeMillis();
        try {
            for (int i = 0; i < TEST_ITERATIONS; i++) {
                GrammarRunner.grammar(new LinkedList<>(lexical), 0, lexical.size() - 1);
            }
        } catch (ExpressionException e) {
            System.err.println("语法分析性能测试发生异常: " + e.getMessage());
            throw e;
        }
        System.out.println("语法分析" + TEST_ITERATIONS + "次耗时:" + (System.currentTimeMillis() - begin) + "ms");
    }
}
