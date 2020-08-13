package com.kejin.compile;


import com.kejin.Node;
import com.kejin.enums.CharType;
import com.kejin.enums.CompileException;
import com.kejin.enums.Dictionary;
import com.kejin.enums.LexicalState;
import com.kejin.operator.Operators;
import com.kejin.var.*;

import java.util.LinkedList;
import java.util.List;


public class LexicalRunner {

    public static List<Node> compile(String line) throws CompileException {
        int size = line.length();
        List<Node> tree = new LinkedList<>();
        LexicalState lexicalState = LexicalState.START;
        char[] fastStack = new char[64];
        int point = 0;
        for (int i = 0; i < size; i++) {
            char aChar = line.charAt(i);
            CharType type = Dictionary.charType(aChar);
            if (lexicalState == LexicalState.TEXT_REDUCTION && type != CharType.TEXT) {
                type = CharType.ARG;
            }
            switch (type) {
                case OPERATOR:
                    switch (lexicalState) {
                        case ARG_REDUCTION:
                            tree.add(toArg(fastStack, point));
                            point = 0;
                            break;
                        case CONST_REDUCTION:
                            tree.add(toConst(fastStack, point));
                            point = 0;
                            break;
                        case START:
                        case OPERATOR_REDUCTION:
                            break;
                        default:
                            throw new CompileException("非法的词法状态" + lexicalState);
                    }
                    lexicalState = LexicalState.OPERATOR_REDUCTION;
                    if (point > 20) {
                        throw new CompileException(new String(fastStack, 0, point) + "运算符长度不能超过20个字符");
                    }
                    fastStack[point++] = aChar;
                    break;
                case NUMBER:
                    switch (lexicalState) {
                        case ARG_REDUCTION:
                            break;
                        case START:
                        case CONST_REDUCTION:
                            lexicalState = LexicalState.CONST_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            toOperator(fastStack, point, tree);
                            point = 0;
                            lexicalState = LexicalState.CONST_REDUCTION;
                            break;
                        default:
                            throw new CompileException("非法的词法状态" + lexicalState);
                    }
                    fastStack[point++] = aChar;
                    break;
                case ARG:
                    switch (lexicalState) {
                        case START:
                        case ARG_REDUCTION:
                        case CONST_REDUCTION:
                            lexicalState = LexicalState.ARG_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            toOperator(fastStack, point, tree);
                            point = 0;
                            lexicalState = LexicalState.ARG_REDUCTION;
                            break;
                        case TEXT_REDUCTION:
                            break;
                        default:
                            throw new CompileException("非法的词法状态" + lexicalState);
                    }
                    if (point > 50) {
                        throw new CompileException(new String(fastStack, 0, point) + "变量命名不能超过50个字符");
                    }
                    fastStack[point++] = aChar;
                    break;
                case TEXT:
                    switch (lexicalState) {
                        case START:
                            lexicalState = LexicalState.TEXT_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            toOperator(fastStack, point, tree);
                            point = 0;
                            lexicalState = LexicalState.TEXT_REDUCTION;
                            break;
                        case TEXT_REDUCTION:
                            tree.add(toText(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.START;
                            break;
                        case ARG_REDUCTION:
                        case CONST_REDUCTION:
                            throw new CompileException("字符串不能出现在变量之后");
                        default:
                            throw new CompileException("非法的词法状态" + lexicalState);
                    }
                    break;
            }

        }
        if (lexicalState == LexicalState.ARG_REDUCTION) {
            tree.add(toArg(fastStack, point));
        } else if (lexicalState == LexicalState.CONST_REDUCTION) {
            tree.add(toConst(fastStack, point));
        } else if (lexicalState == LexicalState.OPERATOR_REDUCTION) {
            toOperator(fastStack, point, tree);
        }
        return tree;
    }

    private static void toOperator(char[] stack, int point, List<Node> tree) throws CompileException {

        String op = new String(stack, 0, point);
        Operators operator = Dictionary.toOperators(op);
        if (operator == null) {
            if (point > 1) {
                int i = 1;
                boolean splitSuccess = false;
                for (; i < point - 1; i++) {
                    op = new String(stack, 0, i);
                    operator = Dictionary.toOperators(op);
                    if (operator != null) {
                        tree.add(operator);
                        splitSuccess = true;
                        break;
                    }
                }
                if (splitSuccess) {
                    op = new String(stack, i, point-i);
                    operator = Dictionary.toOperators(op);
                    if (operator != null) {
                        tree.add(operator);
                    } else {
                        throw new CompileException("运算符无法识别" + op);

                    }
                } else {
                    throw new CompileException("运算符无法识别" + op);
                }
            } else {
                throw new CompileException("运算符无法识别" + op);
            }
        } else {
            tree.add(operator);
        }
    }

    private static Node toText(char[] stack, int point) throws CompileException {
        return TextConst.of(new String(stack, 0, point));
    }

    private static Node toConst(char[] stack, int point) throws CompileException {
        if (stack[0] == '"') {
            if (stack[point - 1] == '"') {
                return TextConst.of(new String(stack, 1, point - 2));
            }
        }
        return NumberConst.of(new String(stack, 0, point));
    }

    private static Node toArg(char[] stack, int point) throws CompileException {
        String argName = new String(stack, 0, point);
        if (argName.equals("true") || argName.equals("false")) {
            return BooleanConst.of(argName);
        }
        Node Node = Dictionary.toOperators(argName);
        if (Node != null) {
            return Node;
        }
        if (argName.endsWith("B")) {
            return BooleanArg.of(argName);
        }
        if (argName.endsWith("T")) {
            return TextArg.of(argName);
        }
        if (argName.endsWith("N")) {
            return NumberArg.of(argName);
        }
        return TextConst.of(argName);
    }
}
