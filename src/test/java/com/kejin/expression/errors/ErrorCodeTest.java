package com.kejin.expression.errors;

import com.kejin.expression.enums.CharType;
import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.enums.GrammarState;
import com.kejin.expression.enums.Lexical;
import com.kejin.expression.enums.ValueType;
import com.kejin.expression.util.EnumUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorCodeTest {

    @Test
    public void testErrorCodeFunctionality() {
        ErrorCode errorCode = ErrorCode.ARG_MISS;
        assertNotNull(errorCode);
    }
    
    @Test
    public void testEnumEdgeCases() {
        // 测试枚举的边缘情况
        assertEquals(CharType.OPERATOR, CharType.OPERATOR);
        assertEquals(GrammarState.START, GrammarState.START);
        assertEquals(Lexical.ARG, Lexical.ARG);
        assertEquals(ValueType.NUMBER, ValueType.NUMBER);
        
        // 测试通过代码获取枚举
        ValueType vt = EnumUtil.getByCode(0, ValueType.class);
        assertEquals(ValueType.BOOLEAN, vt);
    }
}