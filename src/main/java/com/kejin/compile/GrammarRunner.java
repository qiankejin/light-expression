package com.kejin.compile;


import com.kejin.Node;
import com.kejin.enums.CompileException;
import com.kejin.enums.GrammarState;
import com.kejin.enums.Lexical;
import com.kejin.express.*;
import com.kejin.operator.DoubleOperators;
import com.kejin.operator.MethodOperators;
import com.kejin.operator.Operators;
import com.kejin.operator.SingleOperator;
import com.kejin.operator.placeholder.BrokersRight;
import com.kejin.operator.placeholder.Comma;
import com.kejin.var.Var;

import java.util.LinkedList;
import java.util.List;

import static com.kejin.enums.GrammarState.*;

public class GrammarRunner {


    public static Var grammar(List<Node> tree, int start, int end, boolean broker) throws CompileException {
        GrammarState grammarStat = START;
        int brokerDeep = 0;
        int startPoint = start;
        int endPoint = start;
        if (broker) {
            if (tree.get(end) instanceof BrokersRight) {
                tree.remove(start);
                tree.remove(end - 1);
                end = end - 2;
                if (start > end) {
                    throw new CompileException("括号为空");
                }
            } else {
                throw new CompileException("括号未正确关闭");
            }
        }
        int priority = Integer.MAX_VALUE;
        while (start != end) {
            Node ex = tree.get(endPoint);
            Lexical kind = ex.lexical();
            switch (kind) {
                case METHOD:
                    switch (grammarStat) {
                        case START:
                        case SINGLE_REDUCE:
                        case OPERATOR_REDUCE:
                            startPoint = endPoint;
                            grammarStat = METHOD_REDUCE;
                            break;
                        case BROKER_REDUCE:
                            break;
                        default:
                            throw new CompileException("语法错误");
                    }
                    break;
                case OPERATOR:
                    switch (grammarStat) {
                        case START:
                        case ARG_LIST_REDUCE:
                            priority = ((Operators) ex).priority();
                            startPoint = endPoint - 1;
                            grammarStat = OPERATOR_REDUCE;
                            break;
                        case BROKER_REDUCE:
                            break;
                        case OPERATOR_REDUCE:
                            int currentPriority = ((Operators) ex).priority();
                            if (currentPriority < priority) {
                                priority = currentPriority;
                                startPoint = endPoint - 1;
                                grammarStat = OPERATOR_REDUCE;
                                break;
                            } else {
                                express(tree, startPoint, start);
                                endPoint = start;
                                end -= 2;
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
                                throw new CompileException("函数缺少右边参数");
                            }
                            methodExpress(tree, startPoint);
                            endPoint = start;
                            end -= 1;
                            grammarStat = START;
                            continue;
                        default:
                            throw new CompileException("语法错误");
                    }
                    break;

                case BLOCKERS_LEFT:
                    switch (grammarStat) {
                        case START:
                        case SINGLE_REDUCE:
                        case OPERATOR_REDUCE:
                        case METHOD_REDUCE:
                            startPoint = endPoint;
                            grammarStat = BROKER_REDUCE;
                            break;
                        case BROKER_REDUCE:
                            break;
                        default:
                            throw new CompileException("语法错误");
                    }
                    brokerDeep++;
                    break;
                case BLOCKERS_RIGHT:
                    grammarStat = BROKER_REDUCE;
                    brokerDeep--;
                    if (brokerDeep == 0) {
                        grammar(tree, startPoint, endPoint, true);
                        tree.add(startPoint, new BrokersExpress((Var) tree.remove(startPoint)));
                        int less = (endPoint - startPoint);
                        endPoint = start;
                        end -= less;
                        grammarStat = START;
                        continue;
                    }
                    break;
                case ARG:
                    break;
                case ARG_LIST:
                    switch (grammarStat) {
                        case START:
                            priority = ((Operators) ex).priority();
                            startPoint = endPoint - 1;
                            grammarStat = ARG_LIST_REDUCE;
                            break;
                        case BROKER_REDUCE:
                        case OPERATOR_REDUCE:
                        case ARG_LIST_REDUCE:
                            break;
                        default:
                            throw new CompileException("语法错误");
                    }
                    break;
                case SIMPLE_OPERATOR:
                    switch (grammarStat) {
                        case START:
                        case OPERATOR_REDUCE:
                            priority = ((Operators) ex).priority();
                            startPoint = endPoint;
                            grammarStat = SINGLE_REDUCE;
                            break;
                        case SINGLE_REDUCE:
                            int currentPriority = ((Operators) ex).priority();
                            if (currentPriority < priority) {
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
                        default:
                            throw new CompileException("语法错误");
                    }
                    break;


            }
            if (endPoint == end) {
                switch (grammarStat) {
                    case SINGLE_REDUCE:
                        if (end - startPoint < 1) {
                            throw new CompileException("单目运算符缺少右边参数");
                        }
                        singleExpress(tree, startPoint);
                        end = end - 1;
                        break;
                    case OPERATOR_REDUCE:
                        if (startPoint < 0) {
                            throw new CompileException(tree.get(start) + "缺少左边参数");
                        }
                        if (end - startPoint < 2) {
                            throw new CompileException("双目运算符缺少右边参数");
                        }
                        express(tree, startPoint, start);
                        end -= 2;
                        break;
                    case START:
                        if (end - start > 0) {
                            throw new CompileException(tree.get(0) + "和" + tree.get(1) + "之间缺少运算符");
                        }
                        break;
                    case METHOD_REDUCE:
                        if (end - startPoint < 1) {
                            throw new CompileException("函数缺少右边参数");
                        }
                        methodExpress(tree, startPoint);
                        end = end - 1;
                        break;
                    case ARG_LIST_REDUCE:
                        commaExpress(tree, startPoint, end);
                        end = start;
                        break;
                    case BROKER_REDUCE:
                        throw new CompileException("括号未正确关闭");
                    default:
                        throw new CompileException("语法错误");
                }
                endPoint = start;
                grammarStat = START;
            } else {
                endPoint++;
            }
        }
        Node Node = tree.get(start);
        if (Node instanceof Var) {
            return (Var) Node;
        } else {
            throw new CompileException(Node + "不能单独存在");
        }
    }

    private static void commaExpress(List<Node> stack, int start, int end) throws CompileException {
        int state = 1;
        List<Var> argList = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            Node point = stack.remove(start);
            if (state == 1) {
                if (point instanceof Var) {
                    state = 0;
                    argList.add((Var) point);
                } else {
                    throw new CompileException("参数列表逗号位置不对" + point);
                }
            } else {
                if (point instanceof Comma) {
                    state = 1;
                } else {
                    throw new CompileException("参数列表逗号位置不对");
                }
            }
        }

        CommaExpress express = new CommaExpress(argList);
        stack.add(start, express);
    }


    private static void singleExpress(List<Node> stack, int start) throws CompileException {
        Node op = stack.remove(start);
        Node right = stack.remove(start);
        if (op instanceof SingleOperator && right instanceof Var) {
            ((SingleOperator) op).check((Var) right);
            stack.add(start, new SingleExpress((Var) right, (SingleOperator) op));
        } else {
            throw new CompileException("单目运算符语法不对" + op + right);
        }
    }

    private static void methodExpress(List<Node> stack, int start) throws CompileException {
        Node op = stack.remove(start);
        Node right = stack.remove(start);
        if (op instanceof MethodOperators && right instanceof BrokersExpress) {
            ((MethodOperators) op).check(((BrokersExpress) right).getArgList());
            stack.add(start, new MethodExpress((MethodOperators) op, (BrokersExpress) right));
        } else {
            throw new CompileException("函数语法不对" + op + right);
        }
    }

    private static void express(List<Node> stack, int startPoint, int start) throws CompileException {
        if (startPoint < start) {
            throw new CompileException(stack.get(start) + "运算符缺少左边变量");
        }
        Node left = stack.remove(startPoint);
        Node op = stack.remove(startPoint);
        Node right = stack.remove(startPoint);
        if (op instanceof DoubleOperators && left instanceof Var && right instanceof Var) {
            Var leftVar = (Var) left;
            Var rightVar = (Var) right;
            DoubleOperators operator = (DoubleOperators) op;
            operator.check(leftVar, rightVar);
            Var express = new BinaryExpress(leftVar, operator, rightVar);
            stack.add(startPoint, express);
        } else {
            throw new CompileException("双目运算符语法不对" + left + op + right);
        }
    }

}
