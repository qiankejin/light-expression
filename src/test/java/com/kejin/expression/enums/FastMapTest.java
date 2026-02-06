package com.kejin.expression.enums;

import com.kejin.expression.enums.CharType;
import com.kejin.expression.enums.FastMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class FastMapTest {

    @Test
    public void testFastMapEfficiency() {
        // 测试FastMap的初始化和查找功能
        FastMap fastMap = new FastMap(CharType.TEXT);
        fastMap.add('a', CharType.TEXT);
        fastMap.add('b', CharType.OPERATOR);
        fastMap.add('0', CharType.NUMBER);
        fastMap.init();

        assertEquals(CharType.TEXT, fastMap.get('a'));
        assertEquals(CharType.OPERATOR, fastMap.get('b'));
        assertEquals(CharType.NUMBER, fastMap.get('0'));
        assertEquals(CharType.TEXT, fastMap.get('z')); // default type
    }
}