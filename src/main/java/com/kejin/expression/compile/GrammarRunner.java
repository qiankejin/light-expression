package com.kejin.expression.compile;


import com.kejin.expression.Node;
import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.GrammarState;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.OperatorsPriority;
import com.kejin.expression.errors.ExecuteException;
import com.kejin.expression.errors.ExpressionException;
import com.kejin.expression.express.*;
import com.kejin.expression.operator.DoubleOperators;
import com.kejin.expression.operator.MethodOperators;
import com.kejin.expression.operator.Operators;
import com.kejin.expression.operator.SingleOperator;
import com.kejin.expression.operator.calculate.Case;
import com.kejin.expression.operator.calculate.Subtract;
import com.kejin.expression.operator.placeholder.Comma;
import com.kejin.expression.var.NumberConst;
import com.kejin.expression.var.Var;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static com.kejin.expression.enums.GrammarState.*;

public class GrammarRunner {

    /**
     * 语法分析函数，将词法分析后的节点列表转换为表达式树
     * 根据不同的词法类型和当前语法状态进行相应的处理和规约
     *
     * @param tree  节点列表，包含经过词法分析的节点
     * @param start 起始索引，指定语法分析的起始位置
     * @param end   结束索引，指定语法分析的结束位置
     * @return 返回语法分析后生成的变量表达式
     * @throws ExpressionException 当语法错误时抛出异常
     */
    public static Var grammar(List<Node> tree, int start, int end) throws ExpressionException {
        GrammarState grammarStat = START;
        //括号递归深度
        int brokerDeep = 0;
        //语法分析头节点指针
        int startPoint = start;
        //语法分析尾节点指针
        int endPoint = start;

        int priority = Integer.MAX_VALUE;
        while (start != end) {
            Node ex = tree.get(endPoint);
            Lexical kind = ex.lexical();
            switch (kind) {
                case METHOD:
                    //函数
                    switch (grammarStat) {
                        case START:
                        case SINGLE_REDUCE:
                        case OPERATOR_REDUCE:
                        case ARG_LIST_REDUCE:
                            startPoint = endPoint;
                            grammarStat = METHOD_REDUCE;
                            break;
                        case BROKER_REDUCE:
                            break;
                        case ARG:
                        case OPERATOR_ARG:
                        case METHOD_REDUCE:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "不能直接出现在变量后面，中间必须要有双目运算符");
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    break;
                case OPERATOR:
                    //二元操作符
                    switch (grammarStat) {
                        case START:
                            if (ex instanceof Subtract) {
                                priority = OperatorsPriority.LEVEL_MINUS;
                            } else {
                                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "双目操作符不能出现在开头");
                            }
                            startPoint = endPoint - 1;
                            grammarStat = OPERATOR_REDUCE;
                            break;
                        case ARG:
                        case ARG_LIST_REDUCE:
                            priority = ((Operators) ex).priority();
                            startPoint = endPoint - 1;
                            grammarStat = OPERATOR_REDUCE;
                            break;

//                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "双目操作符不能出现在逗号后面");
                        case BROKER_REDUCE:
                            break;
                        case OPERATOR_REDUCE:
                            if (ex instanceof Subtract) {
                                priority = OperatorsPriority.LEVEL_MINUS;
                                startPoint = endPoint - 1;
                                break;
                            } else {
                                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "双目操作符不能连续出现");
                            }
                        case OPERATOR_ARG:
                            int currentPriority = ((Operators) ex).priority();
                            if (currentPriority < priority) {
                                priority = currentPriority;
                                startPoint = endPoint - 1;
                                grammarStat = OPERATOR_REDUCE;
                                break;
                            } else {
                                int less = express(tree, startPoint, start);
                                endPoint = start;
                                end -= less;
                                grammarStat = START;
                                continue;
                            }
                        case SINGLE_REDUCE:
                            singleExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        case METHOD_REDUCE:
                            if (end - startPoint < 1) {
                                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "函数缺少右边参数");
                            }
                            methodExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;

                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    break;
                case BLOCKERS_LEFT:
                    //左括号
                    switch (grammarStat) {
                        case START:
                        case SINGLE_REDUCE:
                        case OPERATOR_REDUCE:
                        case METHOD_REDUCE:
                        case ARG_LIST_REDUCE:
                            startPoint = endPoint;
                            grammarStat = BROKER_REDUCE;
                            break;
                        case BROKER_REDUCE:
                            break;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    brokerDeep++;
                    break;
                case BLOCKERS_RIGHT:
                    //右括号
                    grammarStat = BROKER_REDUCE;
                    brokerDeep--;
                    if (brokerDeep == 0) {
                        if (endPoint - startPoint == 1) {
                            tree.remove(startPoint);
                            tree.remove(startPoint);
                            tree.add(startPoint, BrokersExpress.of(null));
                        } else {
                            //递归括号内的语法解析
                            grammar(tree, startPoint + 1, endPoint - 1);
                            //去除左右括号
                            tree.remove(startPoint);
                            tree.remove(startPoint + 1);
                            tree.add(startPoint, BrokersExpress.of((Var) tree.remove(startPoint)));
                        }
                        //组装成括号表达式
                        int less = (endPoint - startPoint);
                        endPoint = start;
                        end -= less;
                        grammarStat = START;
                        continue;
                    }
                    break;
                case ARG:
                    //变量
                    switch (grammarStat) {
                        case START:
                            grammarStat = ARG;
                            break;
                        case ARG_LIST_REDUCE:
                        case BROKER_REDUCE:
                            break;
                        case METHOD_REDUCE:
                            methodExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        case OPERATOR_REDUCE:
                            grammarStat = OPERATOR_ARG;
                            break;
                        case OPERATOR_ARG:
                        case ARG:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "变量不能连续出现，中间必须要有操作符");
                        case SINGLE_REDUCE:
                            singleExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    break;
                case ARG_LIST:
                    //逗号
                    switch (grammarStat) {
                        case START:
                        case ARG:
                            startPoint = endPoint - 1;
                            grammarStat = ARG_LIST_REDUCE;
                            break;
                        case SINGLE_REDUCE:
                            singleExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        case OPERATOR_REDUCE:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex+"逗号不能出现在双目运算符后面");
                        case OPERATOR_ARG:
                            int less = express(tree, startPoint, start);
                            endPoint = start;
                            end -= less;
                            grammarStat = START;
                            continue;
                        case ARG_LIST_REDUCE:
                        case BROKER_REDUCE:
                            break;
                        case METHOD_REDUCE:
                            if (end - startPoint < 1) {
                                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "函数缺少右边参数");
                            }
                            methodExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    break;
                case SIMPLE_OPERATOR:
                    //单目操作符
                    switch (grammarStat) {
                        case START:
                        case OPERATOR_REDUCE:
                            priority = ((Operators) ex).priority();
                            startPoint = endPoint;
                            grammarStat = SINGLE_REDUCE;
                            break;
                        case SINGLE_REDUCE:
                            int currentPriority = ((Operators) ex).priority();
                            if (currentPriority <= priority) {
                                priority = currentPriority;
                                startPoint = endPoint;
                                grammarStat = SINGLE_REDUCE;
                                break;
                            } else {
                                singleExpress(tree, startPoint);
                                endPoint = start;
                                end -= 1;
                                grammarStat = START;
                                continue;
                            }
                        case BROKER_REDUCE:
                            break;
                        case ARG:
                        case OPERATOR_ARG:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, ex + "单目运算符不能出现在变量后面");
                        default:
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                    }
                    break;
            }
            //结尾处理
            if (endPoint == end) {
                switch (grammarStat) {
                    case SINGLE_REDUCE:
                        throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "单目运算符缺少右边参数");
                    case OPERATOR_REDUCE:
                    case OPERATOR_ARG:
                        if (startPoint < 0) {
                            if (tree.get(0) instanceof Subtract && tree.get(1) instanceof Var) {
                                tree.add(0, NumberConst.of("0"));
                                startPoint = 0;
                                end++;
                            } else {
                                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree.get(start) + "缺少左边参数");
                            }
                        }
                        if (end - startPoint < 2) {
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "双目运算符缺少右边参数");
                        }
                        int less = express(tree, startPoint, start);
                        end -= less;
                        break;
                    case START:
                        if (end - start > 0) {
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree.get(0) + "和" + tree.get(1) + "之间缺少运算符");
                        }
                        break;
                    case METHOD_REDUCE:
                        if (end - startPoint < 1) {
                            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "函数缺少右边参数");
                        }
                        methodExpress(tree, startPoint);
                        end = end - 1;
                        break;
                    case ARG_LIST_REDUCE:
                        commaExpress(tree, startPoint, end);
                        end = start;
                        break;
                    case BROKER_REDUCE:
                        throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "括号未正确关闭");
                    default:
                        throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, tree + "中的" + ex + "出现无法识别的语法错误");
                }
                endPoint = start;
                grammarStat = START;
            } else {
                endPoint++;
            }
        }
        Node node = tree.get(start);
        if (node instanceof Var) {
            return (Var) node;
        } else {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, node + "不能单独存在");
        }
    }


    /**
     * 处理参数列表中的逗号分隔符，将一系列用逗号分隔的参数节点转换为CommaExpress对象
     * 该方法遍历从start到end范围内的节点，识别参数和逗号分隔符的交替模式
     *
     * @param stack 存储语法分析节点的栈
     * @param start 起始索引，指定参数列表的开始位置
     * @param end   结束索引，指定参数列表的结束位置
     * @throws ExpressionException 当参数和逗号分隔符的排列不符合预期模式时抛出异常
     */
    public static void commaExpress(List<Node> stack, int start, int end) throws ExpressionException {
        int state = 1;
        List<Var> argList = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            Node point = stack.remove(start);
            if (state == 1) {
                if (point instanceof Var) {
                    state = 0;
                    argList.add((Var) point);
                } else {
                    throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, point+"参数列表逗号不能连续出现");
                }
            } else {
                if (point instanceof Comma) {
                    state = 1;
                } else {
                    throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "参数列表逗号位置不对");
                }
            }
        }
        stack.add(start, new CommaExpress(argList));
    }


    /**
     * 处理单目运算符，将栈中的单目运算符和操作数组合成单目表达式
     * 从栈中移除运算符和操作数，验证后创建新的单目表达式并放回栈中
     *
     * @param stack 存储语法节点的栈
     * @param start 开始位置，指定在栈中的处理位置
     * @throws ExpressionException 当单目运算符语法不正确时抛出异常
     */
    public static void singleExpress(List<Node> stack, int start) throws ExpressionException {
        Node op = stack.remove(start);
        Node right = stack.remove(start);
        if (op instanceof SingleOperator && right instanceof Var) {
            ((SingleOperator) op).check((Var) right);
            stack.add(start, new SingleExpress((Var) right, (SingleOperator) op));
        } else {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "单目运算符语法不对" + op + right);
        }
    }

    /**
     * 处理方法调用，将栈中的方法运算符和括号表达式组成为方法调用表达式
     * 从栈中移除方法运算符和括号表达式，验证参数后创建新的方法表达式并放回栈中
     *
     * @param stack 存储语法节点的栈
     * @param start 开始位置，指定在栈中的处理位置
     * @throws ExpressionException 当函数语法不正确时抛出异常
     */
    public static void methodExpress(List<Node> stack, int start) throws ExpressionException {
        Node op = stack.remove(start);
        Node right = stack.remove(start);
        if (op instanceof MethodOperators && right instanceof BrokersExpress) {
            ((MethodOperators) op).check((BrokersExpress) right);
            stack.add(start, new MethodExpress((MethodOperators) op, (BrokersExpress) right));
        } else {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, "函数语法不对" + op + right);
        }
    }

    public static int express(List<Node> stack, int startPoint, int start) throws ExpressionException {
        if (startPoint < start) {
            if (stack.get(startPoint + 1) instanceof Subtract && stack.get(startPoint + 2) instanceof Var) {
                Var leftVar = NumberConst.of("0");
                Subtract operator = (Subtract) stack.remove(startPoint + 1);
                Var rightVar = (Var) stack.remove(startPoint + 1);
                operator.check(leftVar, rightVar);
                Var express = new BinaryExpress(leftVar, operator, rightVar);
                stack.add(startPoint + 1, express);
                return 1;
            } else {
                throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, stack.get(start) + "运算符缺少左边变量");
            }
        }
        if (!(stack.get(startPoint) instanceof Var) && stack.get(startPoint + 1) instanceof Subtract) {
            Var leftVar = NumberConst.of("0");
            Subtract operator = (Subtract) stack.remove(startPoint + 1);
            Var rightVar = (Var) stack.remove(startPoint + 1);
            operator.check(leftVar, rightVar);
            Var express = new BinaryExpress(leftVar, operator, rightVar);
            stack.add(startPoint + 1, express);
            return 1;
        }

        Node left = stack.remove(startPoint);
        Node op = stack.remove(startPoint);
        Node right = stack.remove(startPoint);
        if (op instanceof DoubleOperators && left instanceof Var && right instanceof Var) {
            Var leftVar = (Var) left;
            Var rightVar = (Var) right;
            if (op instanceof Case) {
                Case operator = (Case) op;
                operator.check(leftVar, rightVar);
                Var express = new CaseExpress(leftVar, rightVar);
                stack.add(startPoint, express);
            } else {
                DoubleOperators operator = (DoubleOperators) op;
                operator.check(leftVar, rightVar);
                Var express = new BinaryExpress(leftVar, operator, rightVar);
                stack.add(startPoint, express);
            }
            return 2;
        } else {
            throw new ExpressionException(ErrorCode.GRAMMAR_ERROR, stack.get(start) + "双目运算符语法不对" + left + op + right);
        }
    }

}