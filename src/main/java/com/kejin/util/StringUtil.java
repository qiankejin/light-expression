package com.kejin.util;

import java.util.Collection;

public class StringUtil {
    public static String join(String delimiter, Collection<?> collect) {
        StringBuilder sb = new StringBuilder();
        for (Object o : collect) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(o.toString());
        }
        return sb.toString();
    }
}
