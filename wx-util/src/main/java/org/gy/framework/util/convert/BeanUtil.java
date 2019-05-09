package org.gy.framework.util.convert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bean之间属性复制
 */
public class BeanUtil {

    /** 记录日志的变量 */
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     * 
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     */
    @SuppressWarnings({
            "rawtypes",
            "unchecked"
    })
    public static Map convertBean(Object bean) {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);

                    returnMap.put(propertyName, result);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * 
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type,
                                    Map map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);// 获取类属性
            Object obj = type.newInstance(); // 创建 JavaBean 对象

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 功能描述: bean属性复制<br>
     * 
     * @param source 源bean
     * @param target 目标bean
     * @return
     */
    public static void copyProperties(Object source,
                                      Object target) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * 功能描述:list复制 <br>
     * 
     * @param sourList 源list
     * @param clazz 目标list中元素类型class
     * @return
     */
    @SuppressWarnings("all")
    public static List copyList(Object sourList,
                                Class clazz) {
        List dObjs = new ArrayList();
        if (sourList instanceof java.util.List) {
            List sObjs = (List) sourList;
            if (sObjs != null && !sObjs.isEmpty()) {
                for (Object sObj : sObjs) {
                    try {
                        Object dObj = clazz.newInstance();
                        copyProperties(sObj, dObj);
                        dObjs.add(dObj);
                    } catch (InstantiationException e) {
                        logger.error(e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return dObjs;
    }

}
