package com.dem.es.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObjectUtil implements Serializable {

    public static Map<String, Object> obj2Map(Object obj) {
        try {
            if (obj == null) {
                return Collections.EMPTY_MAP;
            }
            Map<String, Object> map = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                //字段值没有的不要
                if (StringUtil.isEmpty(value)) {
                    continue;
                }
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {

        }
        return Collections.EMPTY_MAP;
    }
}
