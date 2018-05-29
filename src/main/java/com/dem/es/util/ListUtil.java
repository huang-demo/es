package com.dem.es.util;

import java.io.Serializable;
import java.util.Collection;

public class ListUtil implements Serializable {
    /**
     * 转化成字符串id
     *
     * @param list
     * @return
     */
    public static String list2Str(Collection<?> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(80);
        for (Object id : list) {
            sb.append(id + ",");
        }
        if (sb != null) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
