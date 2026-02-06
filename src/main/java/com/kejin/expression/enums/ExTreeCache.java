package com.kejin.expression.enums;


import com.kejin.expression.errors.ExpressionException;

import java.io.Closeable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class ExTreeCache<K, V> {
    private final Map<K, V> store;
    private final LinkedList<K> key;
    private final int size;

    public ExTreeCache(int size) {
        this.key = new LinkedList<>();
        this.store = new HashMap<>();
        this.size = size;
    }

    public V get(K k) {
        return store.get(k);
    }

    @FunctionalInterface
    public interface InitFunction<K, F> {
        F apply(K k) throws ExpressionException;
    }

    public <F extends V> F getWithCompile(K k, InitFunction<K, F> init) throws ExpressionException {
        F v = (F) store.get(k);
        if (v == null) {
            synchronized (store) {
                v = (F) store.get(k);
                if (v == null) {
                    v = init.apply(k);
                    put(k, v);
                }
            }
        }
        return v;
    }

    private void put(K k, V v) {
        store.put(k, v);
        if (key.size() > size) {
            K old = key.pollFirst();
            V oldValue = store.remove(old);
            if (oldValue instanceof Closeable) {
                try {
                    ((Closeable) oldValue).close();
                } catch (Exception ignore) {
                }
            }
        }
        key.add(k);
    }

}
