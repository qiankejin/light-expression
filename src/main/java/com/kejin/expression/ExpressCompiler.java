package com.kejin.expression;


import com.kejin.expression.compile.GrammarRunner;
import com.kejin.expression.compile.LexicalRunner;
import com.kejin.expression.enums.ExTreeCache;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.var.Var;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表达式编译器类
 * 提供表达式字符串的编译功能，支持带缓存和不带缓存的编译方式
 * 同时提供获取表达式中使用参数的功能
 */
public class ExpressCompiler {

    private static final ExTreeCache<String, Var> compileCache = new ExTreeCache<>(5000);

    /**
     * 使用缓存编译表达式字符串为Var对象
     * 此方法利用内部缓存机制，对于相同的表达式字符串，会重用已编译的结果以提高性能
     *
     * @param line 要编译的表达式字符串
     * @return 编译后的Var对象，代表解析后的表达式树
     * @throws ExpressionException 当表达式格式错误或编译失败时抛出
     */
    public static Var compileWithCache(String line) throws ExpressionException {
        return compileCache.getWithCompile(line, ExpressCompiler::compile);
    }

    /**
     * 编译表达式字符串为Var对象
     * 此方法执行词法分析和语法分析，将表达式字符串转换为可执行的表达式树
     *
     * @param line 要编译的表达式字符串
     * @return 编译后的Var对象，代表解析后的表达式树
     * @throws ExpressionException 当表达式格式错误或编译失败时抛出
     */
    public static Var compile(String line) throws ExpressionException {
        List<Node> lexical = LexicalRunner.compile(line);
        return GrammarRunner.grammar(lexical, 0, lexical.size() - 1);
    }

    /**
     * 获取表达式中使用的所有参数名
     * 解析表达式并提取其中涉及的所有变量参数名称
     *
     * @param express 表达式字符串
     * @return 包含表达式中使用的所有参数名的集合
     * @throws ExpressionException 当表达式格式错误或解析失败时抛出
     */
    public static Set<String> usedArgs(String express) throws ExpressionException {
        Set<String> args = new HashSet<>();
        if (express == null || express.isEmpty()) {
            return args;
        }
        Var var = compileWithCache(express);
        var.usedArg(args);
        return args;
    }
}
