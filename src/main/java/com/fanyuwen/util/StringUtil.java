package com.fanyuwen.util;

/**
 * @author fanyuwen
 * @create 2019/5/23 14:19
 * @description
 * @modify
 */
public class StringUtil {
    private StringUtil() {
        throw new RuntimeException("非法创建对象" + StringUtil.class + ",该类不能被创建.");
    }

    public static boolean isEmpty(String param) {
        return param == null || "".equals(param.trim());
    }

    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }
}