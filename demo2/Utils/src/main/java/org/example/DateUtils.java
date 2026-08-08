package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转字符串
     */
    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    /**
     * 日期转字符串（指定格式）
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串转日期
     */
    public static Date parse(String dateStr) throws Exception {
        return parse(dateStr, DEFAULT_PATTERN);
    }

    /**
     * 字符串转日期（指定格式）
     */
    public static Date parse(String dateStr, String pattern) throws Exception {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }

    /**
     * 获取当前时间戳
     */
    public static Long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
