package com.kejin.expression.util;

import com.kejin.expression.errors.ExecuteException;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void testDateUtilEdgeCases() {
        // 测试日期工具类的边缘情况
        Date now = new Date();
        String formatted = DateUtil.format(now);
        assertNotNull(formatted);
        
        Date parsed = DateUtil.parse(formatted);
        assertNotNull(parsed);
        
        // 测试带格式的日期解析
        Date parsedWithFormat = DateUtil.parse("2023-01-01", "yyyy-MM-dd");
        assertNotNull(parsedWithFormat);
        
        // 测试无效日期格式
        try {
            DateUtil.parse("invalid-date", "yyyy-MM-dd");
            fail("应该抛出异常");
        } catch (ExecuteException e) {
            // 期望异常
        }
    }
}