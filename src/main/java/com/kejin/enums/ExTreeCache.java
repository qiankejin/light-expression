package com.kejin.enums;


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
        this.store = new HashMap<>(size);
        this.size = size;
    }

    public V get(K k) {
        return store.get(k);
    }

    public <F extends V> F getWithInit(K k, Function<K, F> init) {
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
            store.remove(old);
        }
        key.add(k);
    }
}
