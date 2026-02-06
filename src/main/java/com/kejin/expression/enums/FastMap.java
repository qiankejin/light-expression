package com.kejin.expression.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速字符映射表，通过数组存储字符与类型之间的映射关系，以提高访问速度
 * 使用数组代替HashMap来优化字符查找性能，特别适用于字符分类等场景
 */
public class FastMap {
    private CharType[] data;
    private final CharType defaultCharType;
    private Map<Character, CharType> temp = new HashMap<>();
    private int min = Integer.MAX_VALUE;  // 记录最小字符码点
    private int max = Integer.MIN_VALUE;  // 记录最大字符码点

    public FastMap(CharType defaultType) {
        this.defaultCharType = defaultType;
    }

    public void add(char aChar, CharType charType) {
        if (max < aChar) {
            max = aChar;
        }
        if (min > aChar) {
            min = aChar;
        }
        temp.put(aChar, charType);
    }

    public void init() {
        // 根据实际范围分配数组大小，减少内存浪费
        int range = max >= min ? max - min + 1 : 1;
        data = new CharType[range];
        int length = data.length;
        // 将临时Map中的数据复制到数组中，使用偏移量
        temp.forEach((k, v) -> data[k - min] = v);
        for (int i = 0; i < length; i++) {
            if (data[i] == null) {
                data[i] = defaultCharType;
            }
        }
        temp.clear();
        temp = null;
    }

    public CharType get(char a) {
        // 检查字符是否在有效范围内
        if (a < min || a > max) {
            return defaultCharType;
        }
        return data[a - min];  // 使用偏移量访问数组
    }

}