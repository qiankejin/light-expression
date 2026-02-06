package com.kejin.expression.util;

import com.kejin.expression.util.CollectionUtils;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CollectionUtilsTest {

    @Test
    public void testUtilityClasses() {
        // 测试实用工具类
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertTrue(CollectionUtils.isNotEmpty(list));
        assertFalse(CollectionUtils.isNotEmpty(Collections.emptyList()));
        assertFalse(CollectionUtils.isNotEmpty((List<?>) null));
        
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        assertTrue(CollectionUtils.isNotEmpty(map));
        assertFalse(CollectionUtils.isNotEmpty(new HashMap<>()));
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
        
        Integer[] array = {1, 2, 3};
        assertTrue(CollectionUtils.isNotEmpty(array));
        assertFalse(CollectionUtils.isNotEmpty(new Integer[0]));
        assertFalse(CollectionUtils.isNotEmpty((Object[]) null));
    }
}