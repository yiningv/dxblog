package com.yiningv.dxblog.util;

public abstract class StringUtils {

    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static String ifBlankDefault(String value, String defaultVal) {
        return isBlank(value)? defaultVal : value;
    }
}
