package org.gy.framework.generator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述：
 * 
 */
public class ColumnToPropertyUtil {

    private static final String              DEFAULT_LINE = "_";

    private static final Map<String, String> propertyMap;

    static {
        // 只列举常用的，特殊的需要补充
        propertyMap = new HashMap<String, String>();
        propertyMap.put("smallint", "java.lang.Integer");
        propertyMap.put("tinyint", "java.lang.Integer");
        propertyMap.put("int", "java.lang.Integer");
        propertyMap.put("bigint", "java.lang.Long");
        propertyMap.put("float", "java.lang.Float");
        propertyMap.put("double", "java.lang.Double");
        propertyMap.put("decimal", "java.math.BigDecimal");
        propertyMap.put("char", "java.lang.String");
        propertyMap.put("varchar", "java.lang.String");
        propertyMap.put("datetime", "java.util.Date");
        propertyMap.put("timestamp", "java.util.Date");
        propertyMap.put("time", "java.util.Date");
        propertyMap.put("date", "java.util.Date");
        propertyMap.put("year", "java.util.Date");
    }

    private ColumnToPropertyUtil() {

    }

    /**
     * 功能描述: 根据mysql中的数据类型获取对应的java类型
     * 
     * @param key
     * @return
     */
    public static String getJavaType(String key) {
        String v = propertyMap.get(key);
        return v == null ? "java.lang.String" : v;
    }

    /**
     * 功能描述: 驼峰转换
     * 
     * @param column
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    public static String columnToProperty(String column) {
        if (StringUtils.isBlank(column)) {
            return "";
        } else if (!column.contains(DEFAULT_LINE)) {
            // 不含下划线，全部字母都小写
            return column.toLowerCase();
        } else {
            StringBuilder result = new StringBuilder();
            // 用下划线将原始字符串分割
            String[] columns = column.split(DEFAULT_LINE);
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (StringUtils.isBlank(columnSplit)) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

    }

    public static String defaultIfBlank(String src) {
        return StringUtils.isBlank(src) ? "" : src.trim();
    }

}
