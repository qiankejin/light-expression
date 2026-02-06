package com.kejin.expression.param;

import com.kejin.expression.value.Value;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MapParamTest {

    @Test
    public void testParamClassesWithRealData() {
        // 测试参数类的实际使用
        MapParam mapParam = new MapParam();
        mapParam.put("num", Value.of(100L));
        mapParam.put("text", Value.of("hello"));
        mapParam.put("bool", Value.of(true));
        
        assertEquals(100L, mapParam.get("num").toLong());
        assertEquals("hello", mapParam.get("text").toString());
        assertTrue(mapParam.get("bool").toBoolean());
        
        // 测试ListParam
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("value", 1);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("value", 2);
        dataList.add(item1);
        dataList.add(item2);
        
        ListParam listParam = new ListParam(dataList);
        assertEquals(2, listParam.getListData().size());
        assertEquals(1, listParam.getListData().get(0).get("value").toLong());
        assertEquals(2, listParam.getListData().get(1).get("value").toLong());
    }
}