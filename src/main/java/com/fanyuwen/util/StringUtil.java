package com.fanyuwen.util;

/**
 * @author fanyuwen
 * @create 2019/5/23 14:19
 * @description
 * @modify
 */
public class StringUtil {
    private StringUtil() {
        throw new RuntimeException("�Ƿ���������" + StringUtil.class + ",���಻�ܱ�����.");
    }

    public static boolean isEmpty(String param) {
        return param == null || "".equals(param.trim());
    }

    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }
}