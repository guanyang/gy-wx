package org.gy.framework.util.convert;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 功能描述：自定义日期转换器，将字符串转换成Date
 * 
 * @Author gy
 * @Date 2016年8月7日下午4:39:59
 */
public class CustomDateConverter implements Converter<String, Date> {
    /** 记录日志 */
    protected final Logger  logger        = LoggerFactory.getLogger(getClass());

    private static String[] parsePatterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd", "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMMdd" };

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(source, parsePatterns);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) throws ParseException {
        CustomDateConverter converter = new CustomDateConverter();
        System.out.println(converter.convert("2016-08-07"));
        System.out.println(converter.convert("2016-08-07 10:12"));
        System.out.println(converter.convert("2016-08-07 10:12:34"));
        System.out.println(converter.convert("2016/08/07"));
        System.out.println(converter.convert("2016/08/07 10:12"));
        System.out.println(converter.convert("2016/08/07 10:12:34"));
        System.out.println(converter.convert("20160807"));
        System.out.println(converter.convert("201608071012"));
        System.out.println(converter.convert("20160807101234"));
        System.out.println(converter.convert("20160807101234234"));
    }

}
