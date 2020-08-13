package com.kejin;


import com.kejin.compile.GrammarRunner;
import com.kejin.compile.LexicalRunner;
import com.kejin.enums.CompileException;
import com.kejin.enums.ExTreeCache;
import com.kejin.enums.ValueType;
import com.kejin.var.Var;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressCompiler {
    private static final ExTreeCache<String, Var> compileCache = new ExTreeCache<>(5000);

    public static Var compile(String line) throws CompileException {
        Var var = compileCache.get(line);
        if (var != null) {
            return var;
        }
        List<Node> lexical = LexicalRunner.compile(line);
        var = GrammarRunner.grammar(lexical, 0, lexical.size() - 1, false);
        compileCache.put(line, var);
        return var;
    }

    public static Set<String> usedArgs(String express, ValueType valueType) throws CompileException {
        Set<String> args = new HashSet<>();
        if (express == null || express.equals("")) {
            return args;
        }
        Var var = compile(express);
        if (var.valueType() != valueType) {
            throw new CompileException(express + "计算结果不满足期望" + valueType);
        }
        var.usedArg(args);
        return args;
    }
}
