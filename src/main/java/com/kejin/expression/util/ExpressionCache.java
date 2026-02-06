package com.kejin.expression.util;

import com.kejin.expression.enums.ExTreeCache;
import com.kejin.expression.var.Var;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExpressionCache {
    // 使用多个缓存，每个类型一个缓存
    private final ConcurrentMap<Class<? extends Var>, ExTreeCache<String, ? extends Var>> caches = new ConcurrentHashMap<>();
    private final int maxSize;

    public ExpressionCache() {
        this.maxSize = 1000;
    }

    public ExpressionCache(int maxSize) {
        this.maxSize = maxSize;
    }

    @SuppressWarnings("unchecked")
    private <T extends Var> ExTreeCache<String, T> getCache(Class<T> clazz) {
        return (ExTreeCache<String, T>) caches.computeIfAbsent(clazz, c -> new ExTreeCache<>(this.maxSize));
    }

    public <T extends Var> T get(String key, Class<T> clazz) {
        ExTreeCache<String, T> cache = getCache(clazz);
        return cache.get(key);
    }

    public <T extends Var> T getWithInit(String key, ExTreeCache.InitFunction<String, T> init, Class<T> clazz) {
        ExTreeCache<String, T> cache = getCache(clazz);
        return cache.getWithCompile(key, init);
    }
}