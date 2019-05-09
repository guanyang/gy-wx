/*
 * Copyright (C), 2002-2017, guanyang
 * FileName: CookieUtils.java
 * Author:   guanyang
 * Date:     2017年7月5日 下午2:02:20
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.gy.framework.util.auth.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class CookieUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtils.class);

    private CookieUtils() {
    }

    public static String retrieveCookieValue(final HttpServletRequest request,
                                             String cookieName) {
        final Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        try {
            return URLDecoder.decode(cookie.getValue(), "UTF-8");
        } catch (Exception ex) {
            LOGGER.warn("retrieveCookieValue decode meet Exception, cookie:" + cookie.getValue(), ex);
            return cookie.getValue();
        }
    }

    public static void removeCookie(HttpServletResponse response,
                                    String cookieName,
                                    String cookieDomain,
                                    String cookiePath,
                                    boolean isCookieSecure) {
        addCookie(response, cookieName, "", cookieDomain, cookiePath, isCookieSecure, 0, false);
    }

    public static Cookie getCookie(HttpServletRequest request,
                                   String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 String cookieDomain,
                                 String cookiePath,
                                 boolean isCookieSecure,
                                 Integer maxAge,
                                 boolean isCookieHttpOnly) {
        String cookieValueEncoded = cookieValue;
        try {
            cookieValueEncoded = URLEncoder.encode(cookieValue, "UTF-8");
        } catch (Exception ex) {
            LOGGER.warn("addCookie encode meet Exception, cookie:" + cookieValue, ex);
        }
        Cookie cookie = new Cookie(cookieName, cookieValueEncoded);
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
        cookie.setPath(cookiePath);
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        if (isCookieSecure) {
            cookie.setSecure(true);
        }
        // 判断是否需要HttpOnly，如果需要使用addHttpOnlyCookie添加cookies
        if (isCookieHttpOnly) {
            addHttpOnlyCookie(response, cookie);
        } else {
            response.addCookie(cookie);
        }
    }

    public static void addHttpOnlyCookie(HttpServletResponse response,
                                         Cookie cookie) {
        // 判断对象是否存在null的情况
        if (checkObjIsNull(response) || checkObjIsNull(cookie)) {
            return;
        }

        // 依次取得cookie中的名称、值、最大生存时间、路径、域和是否为安全协议信息
        String cookieName = cookie.getName();
        String cookieValue = cookie.getValue();
        int maxAge = cookie.getMaxAge();
        String path = cookie.getPath();
        String domain = cookie.getDomain();
        boolean isSecure = cookie.getSecure();

        StringBuilder strBufferCookie = new StringBuilder();
        strBufferCookie.append(cookieName + "=" + cookieValue + ";");

        if (maxAge >= 0) {
            strBufferCookie.append("Max-Age=" + cookie.getMaxAge() + ";");
        }

        if (!checkObjIsNull(domain)) {
            strBufferCookie.append("domain=" + domain + ";");
        }

        if (!checkObjIsNull(path)) {
            strBufferCookie.append("path=" + path + ";");
        }

        if (isSecure) {
            strBufferCookie.append("secure;HTTPOnly;");
        } else {
            strBufferCookie.append("HTTPOnly;");
        }

        response.addHeader("Set-Cookie", strBufferCookie.toString());
    }

    private static boolean checkObjIsNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

}
