package org.gy.framework.util.auth.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.Constants;
import org.gy.framework.util.auth.config.Configuration;
import org.gy.framework.util.auth.storage.StorageAccessor;

public class RequestUtils {

    private static final String  ILL_ARG_MSG = "URL parameter name cannot be null or empty.";

    private static final Pattern IP_PATTERN  = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    private RequestUtils() {

    }

    public static void addParameter(StringBuilder sb,
                                    String paramName,
                                    String paramValue) {
        if (isBlankStr(paramName)) {
            throw new IllegalArgumentException(ILL_ARG_MSG);
        }
        sb.append((sb.indexOf("?") < 0) ? '?' : '&');
        sb.append(paramName);
        sb.append('=');
        if (!isBlankStr(paramValue)) {
            sb.append(paramValue);
        }
    }

    public static String getParameter(String url,
                                      String paramName) {
        if (!StringUtils.contains(url, paramName)) {
            return null;
        }
        String parameterValue = null;
        ParamOffsets paramOffsets = getParamOffsets(url, paramName);
        if (paramOffsets != null) {
            if (paramOffsets.valStart < 0) {
                parameterValue = "";
            } else {
                parameterValue = url.substring(paramOffsets.valStart, paramOffsets.valEnd + 1);
            }
        }
        return parameterValue;
    }

    public static String setParameter(String url,
                                      String paramName,
                                      String value) {
        if (isBlankStr(paramName)) {
            throw new IllegalArgumentException(ILL_ARG_MSG);
        }
        StringBuilder returnURL = new StringBuilder(url);
        ParamOffsets paramOffsets = getParamOffsets(url, paramName);
        if (paramOffsets == null) {
            addParameter(returnURL, paramName, value);
        } else {
            if (paramOffsets.valStart < 0) {
                int valStart = paramOffsets.nameStart + paramName.length() + 1;
                returnURL.replace(valStart, valStart, value);
            } else {
                returnURL.replace(paramOffsets.valStart, paramOffsets.valEnd + 1, value);
            }
        }
        return returnURL.toString();
    }

    public static String removeParameter(String url,
                                         String paramName) {
        if (isBlankStr(paramName)) {
            throw new IllegalArgumentException(ILL_ARG_MSG);
        }
        if (!StringUtils.contains(url, paramName)) {
            return url;
        }
        ParamOffsets paramOffsets = getParamOffsets(url, paramName);
        if (paramOffsets != null) {
            StringBuilder sb = new StringBuilder(url);
            int paramEnd = paramOffsets.nameStart + paramName.length(); // If param has no value, remove up to and
            // including =
            if (paramOffsets.valStart >= 0) {
                paramEnd = paramOffsets.valEnd; // If param has a value, remove up to and include value end.
            }
            int separatorIndex = paramOffsets.nameStart - 1; // Index of ? or & just before param name.
            char separator = sb.charAt(separatorIndex);
            sb.replace(separatorIndex, paramEnd + 1, "");
            if (sb.length() > separatorIndex) {
                sb.setCharAt(separatorIndex, separator);
            }
            return sb.toString();
        }
        return url;
    }

    private static Integer getIntParameter(String sourceURL,
                                           String paramName) {
        if (isBlankStr(paramName)) {
            throw new IllegalArgumentException(ILL_ARG_MSG);
        }
        String curParamValueStr = getParameter(sourceURL, paramName);
        if (isBlankStr(curParamValueStr)) {
            return null;
        }
        return Integer.parseInt(curParamValueStr);
    }

    /**
     * class used internal.
     * 
     * @author <a href="mailto:ljw79618@gmail.com">L.J.W</a>
     */
    private static class ParamOffsets {
        int nameStart = -1;
        int valStart  = -1;
        int valEnd    = -1;
    }

    private static ParamOffsets getParamOffsets(String url,
                                                String paramName) {
        if (isBlankStr(paramName)) {
            throw new IllegalArgumentException(ILL_ARG_MSG);
        }
        int lastCharIndex = url.length() - 1;
        char paramSeparator = '?';
        int startIndex = 0;
        while (startIndex <= lastCharIndex) {
            int nextParamIndex = url.indexOf(paramSeparator, startIndex);
            if (nextParamIndex < 0) {
                return null;
            }
            int paramNameIndex = nextParamIndex + 1;
            int charAfterParamNameIndex = paramNameIndex + paramName.length();
            if (charAfterParamNameIndex > lastCharIndex) {
                return null;
            }
            if ((url.charAt(charAfterParamNameIndex) == '=') && url.regionMatches(paramNameIndex, paramName, 0, paramName.length())) {
                ParamOffsets paramOffsets = new ParamOffsets();
                paramOffsets.nameStart = paramNameIndex;
                int paramValueIndex = charAfterParamNameIndex + 1;
                if ((paramValueIndex <= lastCharIndex) && (url.charAt(paramValueIndex) != '&')) {
                    paramOffsets.valStart = paramValueIndex;
                    nextParamIndex = url.indexOf('&', paramValueIndex);
                    if (nextParamIndex == -1) {
                        paramOffsets.valEnd = url.length() - 1;
                    } else {
                        paramOffsets.valEnd = nextParamIndex - 1;
                    }
                }
                return paramOffsets;
            }
            paramSeparator = '&';
            startIndex = paramNameIndex;
        }
        return null;
    }

    private static boolean isBlankStr(String str) {
        return str == null || str.length() == 0;
    }

    public static String buildRequestUrl(HttpServletRequest request,
                                         String serverName) {
        final StringBuilder buffer = new StringBuilder();
        if (serverName == null) {
            String requestUrlFromHeader = request.getHeader("x-request-url");
            if (!isBlankStr(requestUrlFromHeader)) {
                buffer.append(requestUrlFromHeader);
            } else {
                buffer.append(request.isSecure() ? "https://" : "http://").append(request.getServerName()).append(request.getRequestURI());
            }
        } else if (!serverName.startsWith("https://") && !serverName.startsWith("http://")) {
            buffer.append(request.isSecure() ? "https://" : "http://").append(serverName).append(request.getRequestURI());
        } else {
            buffer.append(serverName).append(request.getRequestURI());
        }
        return buffer.toString();
    }

    public static String appendQueryStr(HttpServletRequest request,
                                        String url) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(url);
        String queryStr = request.getQueryString();
        if (!isBlankStr(queryStr)) {
            buffer.append("?");
            buffer.append(queryStr);
        }
        return buffer.toString();
    }

    @SuppressWarnings("unchecked")
    public static String takeRequestSnapshot(HttpServletRequest request,
                                             Configuration configuration) {
        String snapshotId = RandomGenerator.nextId();
        StorageAccessor storageAccessor = configuration.getStorageAccessor();
        Map parameterMap = new HashMap(request.getParameterMap());
        parameterMap.remove(Constants.TARGET_URL_PARAM);
        parameterMap.remove(Constants.TICKET_PARAM);
        parameterMap.remove(Constants.LOGIN_THEME_PARAM);
        parameterMap.remove(Constants.SUPER_TICKET_PARAM);
        parameterMap.remove(Constants.SNAPSHOT_ID_PARAM);
        storageAccessor.saveRequestParamsSnapshot(snapshotId, parameterMap);
        return snapshotId;
    }

    public static String getRealRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            String[] ipArr = ip.split("\\,");
            for (int i = 0; i < ipArr.length; i++) {
                Matcher m = IP_PATTERN.matcher(ipArr[i].trim());
                if (m.find()) {
                    ip = ipArr[i].trim();
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 功能描述: 验证是否是ajax请求，true是，false不是
     * 
     */
    public static boolean isAjax(HttpServletRequest request) {
        return Constants.DEFAULT_AJAX_REQUEST.equalsIgnoreCase(request.getHeader(Constants.DEFAULT_AJAX_REQUEST_HEADER));
    }

    /**
     * 功能描述: 验证是否是微信，true是，false不是
     * 
     * @param request
     * @return
     * @version 2.0.0
     */
    public static boolean isWeChat(HttpServletRequest request) {
        String userAgent = request.getHeader(Constants.USER_AGENT_KEY);
        if (StringUtils.isNotBlank(userAgent) && userAgent.toLowerCase().contains(Constants.WX_USER_AGENT)) {
            return true;
        }
        return false;
    }

}
