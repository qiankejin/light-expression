package com.kejin.expression.enums;


import com.kejin.expression.operator.Operators;
import com.kejin.expression.operator.calculate.*;
import com.kejin.expression.operator.compare.*;
import com.kejin.expression.operator.method.*;
import com.kejin.expression.operator.placeholder.*;
import com.kejin.expression.operator.single.BitNot;
import com.kejin.expression.operator.single.Not;
import com.kejin.expression.operator.window.*;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    /**
     * 运算符
     */
    private static final Map<String, Operators> OPERATOR = new HashMap<>();
    /**
     * 字符类型索引
     */
    private static final FastMap CHAR_TYPE = new FastMap(CharType.ARG);

    static {
        init();
    }

    private static void init() {
        initNumberChar();
        initTextChar();
        initOperatorChar();
        CHAR_TYPE.init();
        register(new And());
        register(new Or());

        register(new BitAnd());
        register(new BitOr());
        register(new BitXOR());
        register(new Divide());
        register(new Mod());
        register(new Multiply());

        register(new Plus());
        register(new Case());
        register(new ShiftLeftWithSign());
        register(new ShiftRight());
        register(new ShiftRightWithSign());
        register(new Subtract());
        register(new Equal());
        register(new GreaterThan());
        register(new GreaterThanOE());
        register(new LessThan());
        register(new LessThanOE());
        register(new NotEqual());
        register(new Concat());
        register(new Cos());
        register(new Sin());
        register(new HalfUp());
        register(new IF());
        register(new IN());
        register(new Max());
        register(new NotIn());
        register(new Power());
        register(new RoundDown());
        register(new RoundUp());
        register(new Sqrt());
        register(new Switch());
        register(new RoundStep());
        register(new BrokersLeft());
        register(new BrokersRight());
        register(new Comma());
        register(new BitNot());
        register(new Not());
        register(new AverageMethod());
        register(new CountMethod());
        register(new GroupMaxMethod());
        register(new GroupMinMethod());
        register(new SumMethod());
        register(new ToDate());
        register(new DateFormat());
        register(new ToNumber());
        register(new DateAdd());
        register(new DateStart());
        register(new DateEnd());
        register(new IsNull());
        register(new Seq());
        register(new IFF());
        register(new GFirstMethod());
        register(new Eval());
        register(new DateSub());
        register(new Min());
        register(new ABS());
        register(new Length());
        register(new Upper());
        register(new Lower());
        register(new SubString());
        register(new ToString());
        register(new IsEmpty());
        register(new Inverse());
    }

    private static void initCalculateOperator(){

    }

    private static void initTextChar() {
        CHAR_TYPE.add('"', CharType.TEXT);
    }

    private static void initOperatorChar() {
        CHAR_TYPE.add('+', CharType.OPERATOR);
        CHAR_TYPE.add('-', CharType.OPERATOR);
        CHAR_TYPE.add('*', CharType.OPERATOR);
        CHAR_TYPE.add('/', CharType.OPERATOR);
        CHAR_TYPE.add('(', CharType.OPERATOR);
        CHAR_TYPE.add('%', CharType.OPERATOR);
        CHAR_TYPE.add(')', CharType.OPERATOR);
        CHAR_TYPE.add('!', CharType.OPERATOR);
        CHAR_TYPE.add('^', CharType.OPERATOR);
        CHAR_TYPE.add(',', CharType.OPERATOR);
        CHAR_TYPE.add('~', CharType.OPERATOR);
        CHAR_TYPE.add('>', CharType.OPERATOR);
        CHAR_TYPE.add('<', CharType.OPERATOR);
        CHAR_TYPE.add('=', CharType.OPERATOR);
        CHAR_TYPE.add(':', CharType.OPERATOR);
        CHAR_TYPE.add('&', CharType.OPERATOR);
        CHAR_TYPE.add('|', CharType.OPERATOR);
    }

    private static void initNumberChar() {
        CHAR_TYPE.add('1', CharType.NUMBER);
        CHAR_TYPE.add('2', CharType.NUMBER);
        CHAR_TYPE.add('3', CharType.NUMBER);
        CHAR_TYPE.add('4', CharType.NUMBER);
        CHAR_TYPE.add('5', CharType.NUMBER);
        CHAR_TYPE.add('6', CharType.NUMBER);
        CHAR_TYPE.add('7', CharType.NUMBER);
        CHAR_TYPE.add('8', CharType.NUMBER);
        CHAR_TYPE.add('0', CharType.NUMBER);
        CHAR_TYPE.add('9', CharType.NUMBER);
        CHAR_TYPE.add('.', CharType.NUMBER);
    }

    public static synchronized void register(Operators operators) {
        OPERATOR.put(operators.symbol(), operators);
    }

    public static CharType charType(char aChar) {
        return CHAR_TYPE.get(aChar);
    }

    public static Operators toOperators(String operator) {
        return OPERATOR.get(operator);
    }
}