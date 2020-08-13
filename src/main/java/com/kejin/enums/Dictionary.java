package com.kejin.enums;


import com.kejin.operator.Operators;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        CHAR_TYPE.add('&', CharType.OPERATOR);
        CHAR_TYPE.add('|', CharType.OPERATOR);
        CHAR_TYPE.init(CharType.ARG);
        InputStream input = Dictionary.class.getClassLoader().getResourceAsStream("operator.conf");
        if (input == null) {
            throw new RuntimeException("找不到运算符配置文件");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Operators operators = (Operators) Class.forName(line).newInstance();
                OPERATOR.put(operators.symbol(), operators);
            }
        } catch (Exception e) {
            throw new RuntimeException("运算符配置文件读取错误", e);

        }
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
