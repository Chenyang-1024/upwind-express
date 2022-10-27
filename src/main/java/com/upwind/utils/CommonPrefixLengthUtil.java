package com.upwind.utils;

/**
 * 获取两字符串最长匹配前缀长度
 **/
public class CommonPrefixLengthUtil {

    public static Integer getStrCommonPrefixLength (String str1, String str2) {
        int a = str1.length();
        int b = str2.length();
        int len = 0;
        for (int i=0; i<a && i<b; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                len = i;
                break;
            }
            else {
                if (i == (a-1))
                    len = a;
                if (i == (b-1))
                    len = b;
            }
        }
        return len;
    }

}
