package com.kejin;


import com.kejin.compile.GrammarRunner;
import com.kejin.compile.LexicalRunner;
import com.kejin.enums.ExTreeCache;
import com.kejin.var.Var;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressCompiler {

    private static final ExTreeCache<String, Var> compileCache = new ExTreeCache<>(5000);

    public static Var compileWithCache(String line)  {
        return compileCache.getWithInit(line, ExpressCompiler::compile);
    }

    public static Var compile(String line) {
        List<com.kejin.Node> lexical = LexicalRunner.compile(line);
        return GrammarRunner.grammar(lexical, 0, lexical.size() - 1, false);
    }

    public static Set<String> usedArgs(String express)  {
        Set<String> args = new HashSet<>();
        if (express == null || express.equals("")) {
            return args;
        }
        Var var = compileWithCache(express);
        var.usedArg(args);
        return args;
    }
}
