package com.kejin.expression.compile;


import com.kejin.expression.Node;
import com.kejin.expression.enums.CharType;
import com.kejin.expression.enums.Dictionary;
import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.LexicalState;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.operator.Operators;
import com.kejin.expression.var.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * 词法分析器，负责将输入的字符串表达式转换为节点列表
 * 通过状态机的方式逐字符分析输入字符串，识别操作符、数字、文本、变量等不同类型的词法单元
 */
public class LexicalRunner {


    /**
     * 将输入的字符串表达式编译为节点列表
     * 通过状态机的方式逐字符分析输入字符串，识别操作符、数字、文本、变量等不同类型的词法单元
     *
     * @param line 输入的字符串表达式
     * @return 解析后的节点列表
     * @throws ExpressionException 当表达式语法错误时抛出异常
     */
    public static List<Node> compile(String line) throws ExpressionException {
        if (line == null || line.isEmpty()) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "表达式不能为空");
        }
        int size = line.length();
        List<Node> tree = new LinkedList<>();
        LexicalState lexicalState = LexicalState.START;
        char[] fastStack = new char[line.length()];
        int point = 0;
        for (int i = 0; i < size; i++) {
            char aChar = line.charAt(i);
            //空字符
            if (isEmptyChar(lexicalState, aChar)) {
                continue;
            }
            CharType type = Dictionary.charType(aChar);

            switch (type) {
                case OPERATOR:
                    switch (lexicalState) {
                        case ARG_REDUCTION:
                            tree.add(toArg(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.OPERATOR_REDUCTION;
                            break;
                        case NUMBER_REDUCTION:
                            tree.add(toNumber(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.OPERATOR_REDUCTION;
                            break;
                        case START:
                        case OPERATOR_REDUCTION:
                            lexicalState = LexicalState.OPERATOR_REDUCTION;
                            break;
                        case TEXT_REDUCTION:
                            break;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "非法的词法状态" + lexicalState);
                    }
                    fastStack[point++] = aChar;
                    break;
                case NUMBER:
                    switch (lexicalState) {
                        case ARG_REDUCTION:
                        case TEXT_REDUCTION:
                            break;
                        case START:
                        case NUMBER_REDUCTION:
                            lexicalState = LexicalState.NUMBER_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            tree.addAll(toOperator(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.NUMBER_REDUCTION;
                            break;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "非法的词法状态" + lexicalState);
                    }
                    fastStack[point++] = aChar;
                    break;
                case ARG:
                    switch (lexicalState) {
                        case START:
                        case ARG_REDUCTION:
                        case NUMBER_REDUCTION:
                            lexicalState = LexicalState.ARG_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            tree.addAll(toOperator(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.ARG_REDUCTION;
                            break;
                        case TEXT_REDUCTION:
                            break;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "非法的词法状态" + lexicalState);
                    }
                    fastStack[point++] = aChar;
                    break;
                case TEXT:
                    switch (lexicalState) {
                        case START:
                            lexicalState = LexicalState.TEXT_REDUCTION;
                            break;
                        case OPERATOR_REDUCTION:
                            tree.addAll(toOperator(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.TEXT_REDUCTION;
                            break;
                        case TEXT_REDUCTION:
                            tree.add(toText(fastStack, point));
                            point = 0;
                            lexicalState = LexicalState.START;
                            break;
                        case ARG_REDUCTION:
                        case NUMBER_REDUCTION:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "字符串不能出现在变量之后");
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "非法的词法状态" + lexicalState);
                    }
                    break;
            }

        }
        if (lexicalState == LexicalState.ARG_REDUCTION) {
            tree.add(toArg(fastStack, point));
        } else if (lexicalState == LexicalState.NUMBER_REDUCTION) {
            tree.add(toNumber(fastStack, point));
        } else if (lexicalState == LexicalState.OPERATOR_REDUCTION) {
            tree.addAll(toOperator(fastStack, point));
        } else if (lexicalState == LexicalState.TEXT_REDUCTION) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "字符串没有正确结束");
        }
        return tree;
    }

    /**
     * 判断字符是否为空白字符（在特定状态下需要忽略的字符）
     * 在TEXT_REDUCTION状态下不会忽略空白字符，其他状态下会忽略空格和换行符
     *
     * @param lexicalState 当前词法分析状态
     * @param aChar        待判断的字符
     * @return 如果是应该忽略的空白字符则返回true，否则返回false
     */
    private static boolean isEmptyChar(LexicalState lexicalState, char aChar) {
        return lexicalState != LexicalState.TEXT_REDUCTION && (aChar == ' ' || aChar == '\n');
    }

    /**
     * 将字符数组中的操作符字符串转换为操作符节点列表
     * 尝试将连续的操作符字符作为一个整体操作符解析，如果失败则尝试拆分为多个操作符
     *
     * @param stack 存储待转换字符的数组
     * @param point 数组中有效字符的数量
     * @return 解析后的操作符节点列表
     * @throws ExpressionException 当操作符无法识别时抛出异常
     */
    public static List<Operators> toOperator(char[] stack, int point) throws ExpressionException {
        //第一步判断整体是否是合法操作符
        String op = new String(stack, 0, point);
        Operators operator = Dictionary.toOperators(op);
        if (operator != null) {
            return Collections.singletonList(operator);
        }
        //如果运算符只有一个字符且不合法直接报错
        if (point <= 1) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "运算符无法识别" + op);
        }
        //第二步尝试拆分运算符
        List<Operators> operators = new ArrayList<>();
        int start = 0;//从第一个字符开始
        int length = point-1;//尝试排除一个字符
        while (start < point && length >=0) {
            operator = Dictionary.toOperators(new String(stack, start, length));
            //如果能识别运算符，则加入运算符列表，头指针后移，尝试下一个字符
            if (operator != null) {
                operators.add(operator);
                start += length;
                length = point-start;
            } else {
                //无法识别则尝试多一个字符加入解析
                length--;
            }
        }
        //如果头指针无法移动到末尾，则说明运算符不合法
        if (start < point) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "运算符无法识别" + op);
        }

        //如果出现运算符列表为空，则说明运算符不合法
        if (operators.isEmpty()) {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "运算符无法识别" + op);
        }
        return operators;
    }

    /**
     * 将字符数组中的文本内容转换为文本常量节点
     *
     * @param stack 存储待转换字符的数组
     * @param point 数组中有效字符的数量
     * @return 创建的文本常量节点
     */
    public static Node toText(char[] stack, int point) {
        return TextConst.of(new String(stack, 0, point));
    }

    /**
     * 将字符数组中的数字内容转换为数字常量节点
     *
     * @param stack 存储待转换字符的数组
     * @param point 数组中有效字符的数量
     * @return 创建的数字常量节点
     */
    public static Node toNumber(char[] stack, int point) {
        return NumberConst.of(new String(stack, 0, point));
    }

    /**
     * 将字符数组中的参数内容转换为相应的参数节点
     * 根据参数的后缀字母（B/T/N/D）或特殊值（true/false）确定参数类型
     *
     * @param stack 存储待转换字符的数组
     * @param point 数组中有效字符的数量
     * @return 创建的参数节点
     */
    public static Node toArg(char[] stack, int point) {
        String arg = new String(stack, 0, point);
        if (arg.equals("true")) {
            return BooleanConst.TRUE_CONST;
        }
        if (arg.equals("false")) {
            return BooleanConst.FALSE_CONST;
        }
        Node node = Dictionary.toOperators(arg);
        if (node != null) {
            return node;
        }
        char endChar = stack[point - 1];
        if (endChar == 'B') {
            return BooleanArg.of(arg);
        }
        if (endChar == 'T') {
            return TextArg.of(arg);
        }
        if (endChar == 'N') {
            return NumberArg.of(arg);
        }
        if (endChar == 'D') {
            return DateArg.of(arg);
        }
        return TextConst.of(arg);
    }
}
