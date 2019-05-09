package org.gy.framework.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.json.JacksonMapper;

/**
 * 功能描述：正则检验工具类
 * 
 */
public class RegexUtil {

    public static final String DEFAULT_NAME          = "系統会员";

    public static final String DEFAULT_NAME_TEMPLATE = "[a-zA-Z0-9１２３４５６７８９０ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ～！。？＠＃￥％……＆×（）——＋＝－《》~`@#$%,，^&*()\\-_+=<>¥\"{}:;.?!'\\[\\]\u0020\u3000\uA1A1\u2006\u4E00-\u9FA5]";
    /**
     * 重命名指令前缀
     */
    public static final String CMD_RENAME_PREFIX     = "cpxrn@";
    /**
     * 名称重置指令前缀
     */
    public static final String CMD_RESET_PREFIX      = "cpxrs@";
    /**
     * 指令结束后缀
     */
    public static final String CMD_SUFFIX            = "#";

    public static final String renameCmd             = "^" + CMD_RENAME_PREFIX + DEFAULT_NAME_TEMPLATE + "+" + CMD_SUFFIX + "$";

    public static final String resetCmd              = "^" + CMD_RESET_PREFIX + CMD_SUFFIX + "$";

    /**
     * 功能描述: 校验方法
     * 
     * @param regex 正则表达式
     * @param source 待校验字符串
     * @return boolean 是否匹配，true匹配，false不匹配
     */
    public static boolean isMatch(String regex,
                                  String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        return Pattern.matches(regex, source);
    }

    public static boolean isWxChinese(String name) {
        return isMatch("^[\u4e00-\u9fa5]{4,8}$", name);// 中文字符：[\u4e00-\u9fa5]
    }

    /**
     * 功能描述: 手机号验证
     * 
     * @param str 待验证手机号
     * @return 验证通过返回true
     */
    public static boolean isMobile(String source) {
        return isMatch("^[1][3,4,5,7,8][0-9]{9}$", source);
    }

    /**
     * 功能描述: ip地址验证
     * 
     * @param source 待验证ip地址
     * @return 验证通过返回true
     */
    public static boolean isIpAddress(String source) {
        return isMatch("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$", source);
    }

    /**
     * 功能描述: 密码规则验证
     * 
     * @param str 待验证密码
     * @return 验证通过返回true
     */
    public static boolean isPassword(String source) {
        return isMatch("^\\S{6,20}$", source);
    }

    public static boolean isOrganization(String source) {
        return isMatch("^\\S{2,60}$", source);
    }

    /**
     * 功能描述: 验证码规则验证
     * 
     * @param str 待验证验证码
     * @return 验证通过返回true
     */
    public static boolean isVerifyCode(String source) {
        return isMatch("^[0-9]{4,6}$", source);
    }

    /**
     * 功能描述: 中文验证
     * 
     * @param source
     * @return
     */
    public static boolean isChinese(String source) {
        return isMatch("^[\u4e00-\u9fa5]{1,}$", source);
    }

    /**
     * 功能描述: 邮箱验证
     * 
     * @param email 待验证邮箱
     * @return 验证通过返回true
     * @version 2.0.0
     * @author guanyang
     */
    public static boolean isEmail(String email) {
        String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        // String regex
        // ="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        return isMatch(regex, email);
    }

    /**
     * 功能描述: 过滤名字中特殊字符，emoji表情，如果原值为空或过滤之后为空，则设置成默认值
     * 
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        return filterEmoji(source, DEFAULT_NAME);
    }

    /**
     * 功能描述: 过滤名字中特殊字符，emoji表情
     * 
     * @param source 原字符串
     * @param defaultName 默认值，如果原值为空或过滤之后为空，则设置成默认值
     * @return
     */
    public static String filterEmoji(String source,
                                     String defaultName) {
        if (StringUtils.isNotBlank(source)) {
            Pattern pattern = Pattern.compile(getNameRegex());
            Matcher matcher = pattern.matcher(source);
            StringBuilder builder = new StringBuilder();
            while (matcher.find()) {
                builder.append(matcher.group());
            }
            source = builder.toString();
        }
        // 如果原值为空或过滤之后为空，则设置成默认值
        if (StringUtils.isBlank(source)) {
            source = defaultName;
        }
        return source;
    }

    /**
     * 功能描述: 验证名字是否符合指定字符，true符合，false不符合
     * 
     * @param source
     * @return
     */
    public static boolean validateName(String source) {
        return isMatch(getNameRegex(), source);
    }

    /**
     * 功能描述: 指定字符正则表达式
     * 
     * @return
     */
    private static String getNameRegex() {
        // \u3000\uA1A1\u2006匹配空格
        // \u4E00-\u9FA5匹配中文
        // 如果使用了^和$，则输入的整个字符串作为整体来匹配，此处不需要使用
        return DEFAULT_NAME_TEMPLATE + "+";
    }

    // public static String filterEmoji(String source) {
    // if (source != null) {
    // Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
    // Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    // Matcher emojiMatcher = emoji.matcher(source);
    // if (emojiMatcher.find()) {
    // source = emojiMatcher.replaceAll("*");
    // return source;
    // }
    // return source;
    // }
    // return source;
    // }

    public static WeixinCmd validateWeixinCmd(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        WeixinCmd cmd = null;
        if (source.startsWith(CMD_RENAME_PREFIX)) {
            boolean flag = isMatch(renameCmd, source);
            if (flag) {
                cmd = new WeixinCmd();
                int endIndex = source.lastIndexOf(CMD_SUFFIX);
                String value = source.substring(CMD_RENAME_PREFIX.length(), endIndex);
                cmd.setCode(CMD_RENAME_PREFIX);
                cmd.setValue(value);
            }
        } else if (source.startsWith(CMD_RESET_PREFIX)) {
            boolean flag = isMatch(resetCmd, source);
            if (flag) {
                cmd = new WeixinCmd();
                cmd.setCode(CMD_RESET_PREFIX);
            }
        }
        return cmd;
    }

    public static class WeixinCmd {
        /**
         * 指令标识
         */
        private String code;
        /**
         * 解析值
         */
        private String value;

        /**
         * 获取指令标识
         * 
         * @return code 指令标识
         */
        public String getCode() {
            return code;
        }

        /**
         * 设置指令标识
         * 
         * @param code 指令标识
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * 获取解析值
         * 
         * @return value 解析值
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置解析值
         * 
         * @param value 解析值
         */
        public void setValue(String value) {
            this.value = value;
        }

    }

    public static void main(String[] args) {
        System.out.println(isMobile("12345678900"));
        System.out.println(isChinese("s测试"));
        System.out.println(isEmail("1200@qq.com"));
        System.out.println(validateName("//\\||/"));
        System.out.println(filterEmoji("。。。"));

        WeixinCmd cmd = validateWeixinCmd("cpxrn@qwe#");
        System.out.println(JacksonMapper.beanToJson(cmd));
        cmd = validateWeixinCmd("cpxrs@#");
        System.out.println(JacksonMapper.beanToJson(cmd));

    }

}
