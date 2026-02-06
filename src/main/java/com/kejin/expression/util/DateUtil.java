package com.kejin.expression.util;

import com.kejin.expression.enums.ErrorCode;
import com.kejin.expression.errors.ExecuteException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Map<String, ThreadLocal<SimpleDateFormat>> FORMATTER_CACHE = new ConcurrentHashMap<>();

    private static SimpleDateFormat getFormatter(String format) {
        return FORMATTER_CACHE.computeIfAbsent(format, s -> ThreadLocal.withInitial(() -> new SimpleDateFormat(s))).get();
    }

    public static Date parse(String text) {
        try {
            return getFormatter(DEFAULT_FORMAT).parse(text);
        } catch (Exception e) {
            throw new ExecuteException(ErrorCode.FORMAT_ERROR, text, e);
        }
    }

    public static Date parse(String text, String format) {
        try {
            return getFormatter(format).parse(text);
        } catch (Exception e) {
            throw new ExecuteException(ErrorCode.FORMAT_ERROR, text, e);
        }
    }

    public static String format(Date value) {
        return getFormatter(DEFAULT_FORMAT).format(value);
    }
}
