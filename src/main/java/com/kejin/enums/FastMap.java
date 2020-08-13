package com.kejin.enums;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("uncheck")
public class FastMap<T> {
    private T[] data;
    private T def;
    private int length;
    private Map<Character, T> temp = new HashMap<>();
    private int max;

    public void add(char k, T v) {
        if (max < k) {
            max = k;
        }
        temp.put(k, v);
    }

    @SuppressWarnings("unchecked")
    public void init(T defaultValue) {
        this.def = defaultValue;
        data = (T[]) new Object[max + 1];
        this.length = data.length;
        temp.forEach((k, v) -> data[k] = v);
        if (defaultValue != null) {
            for (int i = 0; i < length; i++) {
                if (data[i] == null) {
                    data[i] = defaultValue;
                }
            }
        }
        temp = null;
    }

    public T get(char a) {
        if (a >= length) {
            return def;
        }
        return data[a];
    }

}
