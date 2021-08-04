package com.kejin.enums;


import com.kejin.operator.Operators;
import com.kejin.operator.calculate.*;
import com.kejin.operator.compare.*;
import com.kejin.operator.method.*;
import com.kejin.operator.placeholder.BrokersLeft;
import com.kejin.operator.placeholder.BrokersRight;
import com.kejin.operator.placeholder.Comma;
import com.kejin.operator.single.BitNot;
import com.kejin.operator.single.Not;

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
    private static final FastMap<CharType> CHAR_TYPE = new FastMap<>();

    static {
        init();
    }

    private static void init() {
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
        CHAR_TYPE.add('"', CharType.TEXT);
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
        CHAR_TYPE.init(CharType.ARG);
        register(new And());
        register(new BitAnd());
        register(new BitOr());
        register(new BitXOR());
        register(new Divide());
        register(new Eval());
        register(new Mod());
        register(new Multiply());
        register(new Or());
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
