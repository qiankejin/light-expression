package com.kejin.expression.util;

import com.kejin.expression.enums.IntegerEnum;

import java.util.HashMap;
import java.util.Map;

public class EnumUtil {

    private static final Map<Class<? extends IntegerEnum>, Map<Integer, ? extends IntegerEnum>> integerEnumCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends IntegerEnum> T getByCode(int code, Class<T> clazz) {
        if (!clazz.isEnum()) {
            return null;
        }
        Map<Integer, T> cache = (Map<Integer, T>) integerEnumCache.get(clazz);
        if (cache == null) {
            synchronized (integerEnumCache) {
                cache = (Map<Integer, T>) integerEnumCache.get(clazz);
                if (cache == null) {
                    cache = new HashMap<>();
                    T[] enumConstants = clazz.getEnumConstants();
                    for (T enumConstant : enumConstants) {
                        cache.put(enumConstant.getCode(), enumConstant);
                    }
                    integerEnumCache.put(clazz, cache);
                }
            }
        }
        return cache.get(code);
    }

}
