package com.kejin.expression.enums;

import com.kejin.expression.enums.CharType;
import com.kejin.expression.enums.Dictionary;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @Test
    public void testDictionaryFunctionality() {
        // 测试Dictionary的各种功能
        assertNotNull(Dictionary.toOperators("IF"));
        assertNotNull(Dictionary.toOperators("+"));
        assertNotNull(Dictionary.toOperators("="));
        assertNotNull(Dictionary.toOperators("=="));
        assertNotNull(Dictionary.toOperators("-"));
        assertNotNull(Dictionary.toOperators("*"));
        assertNotNull(Dictionary.toOperators("/"));
        // 测试字符类型识别
        assertEquals(CharType.OPERATOR, Dictionary.charType('+'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('-'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('*'));
        assertEquals(CharType.OPERATOR, Dictionary.charType('/'));
        assertEquals(CharType.NUMBER, Dictionary.charType('1'));
        // 字母字符被视为ARG（参数）
        assertEquals(CharType.ARG, Dictionary.charType('a'));
        
        // 测试操作符注册
        Dictionary.register(new com.kejin.expression.operator.calculate.Plus());
    }
}