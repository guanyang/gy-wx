package org.gy.framework.util.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.config.Configuration;
import org.gy.framework.util.auth.principal.AuthPrincipal;
import org.gy.framework.util.auth.storage.StorageAccessor;
import org.gy.framework.util.auth.util.CookieUtils;
import org.gy.framework.util.auth.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private AuthHandler() {

    }

    public static AuthResult handler(ServletRequest servletRequest,
                                     ServletResponse servletResponse,
                                     Configuration configuration) {
        AuthResult result = new AuthResult();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String sessionIdentifier = CookieUtils.retrieveCookieValue(request, configuration.getAuthIdCookieName());
        if (StringUtils.isBlank(sessionIdentifier)) {
            logger.info("Can not find authId cookie," + RequestUtils.getRealRemoteAddress(request));
            result.setAuthStatus(false);
            return result;
        }
        result.setSessionIdentifier(sessionIdentifier);
        StorageAccessor storageAccessor = configuration.getStorageAccessor();
        AuthPrincipal principal = storageAccessor.getPrincipal(sessionIdentifier);
        if (principal == null) {
            // if can not found principal, remove cookie
            logger.info("Can not find principal basing authId " + sessionIdentifier + "," + RequestUtils.getRealRemoteAddress(request));
            CookieUtils.removeCookie(response, configuration.getAuthIdCookieName(), configuration.getCookieDomain(), Constants.DEFAULT_COOKIE_PATH, false);
            result.setAuthStatus(false);
            return result;
        }
        result.setPrincipal(principal);
        ServletRequest servletRequestWrapper = new AuthHttpServletRequestWrapper(request, principal, configuration);
        result.setServletRequestWrapper(servletRequestWrapper);
        result.setAuthStatus(true);
        return result;

    }

    public static void redirectHandler(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Configuration configuration,
                                       Configuration.AccessPolicy policy) throws IOException {
        if (RequestUtils.isAjax(request)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"authStatus\":false,\"policy\":\"" + policy + "\"}");
            writer.flush();
            return;
        }
        String currentUrl;
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            // if method is get, service parameter is http://app/auth?targetUrl=http:/app/resource?param1=value1
            currentUrl = request.getRequestURL().toString();
            currentUrl = RequestUtils.appendQueryStr(request, currentUrl);
            currentUrl = RequestUtils.removeParameter(currentUrl, Constants.TICKET_PARAM);
            currentUrl = RequestUtils.removeParameter(currentUrl, Constants.LOGIN_THEME_PARAM);
            currentUrl = RequestUtils.removeParameter(currentUrl, Constants.SUPER_TICKET_PARAM);
            currentUrl = RequestUtils.removeParameter(currentUrl, Constants.SNAPSHOT_ID_PARAM);
            currentUrl = RequestUtils.removeParameter(currentUrl, Constants.TARGET_URL_PARAM);
        } else {
            // if method is post, service parameter is http://app/auth?targetUrl=http://app/resource?snapshotId=123232
            String snapshotId = RequestUtils.takeRequestSnapshot(request, configuration);
            currentUrl = request.getRequestURL().toString();
            currentUrl = RequestUtils.setParameter(currentUrl, Constants.SNAPSHOT_ID_PARAM, snapshotId);
        }
        String redirect = RequestUtils.setParameter(configuration.getAuthUrl(), Constants.TARGET_URL_PARAM, URLEncoder.encode(currentUrl, "UTF-8"));
        response.sendRedirect(redirect);
    }

    public static class AuthResult {

        private boolean        authStatus = false;

        private String         sessionIdentifier;

        private AuthPrincipal  principal;

        private ServletRequest servletRequestWrapper;

        /**
         * 获取 authStatus
         * 
         * @return authStatus authStatus
         */
        public boolean isAuthStatus() {
            return authStatus;
        }

        /**
         * 设置 authStatus
         * 
         * @param authStatus authStatus
         */
        public void setAuthStatus(boolean authStatus) {
            this.authStatus = authStatus;
        }

        /**
         * 获取 sessionIdentifier
         * 
         * @return sessionIdentifier sessionIdentifier
         */
        public String getSessionIdentifier() {
            return sessionIdentifier;
        }

        /**
         * 设置 sessionIdentifier
         * 
         * @param sessionIdentifier sessionIdentifier
         */
        public void setSessionIdentifier(String sessionIdentifier) {
            this.sessionIdentifier = sessionIdentifier;
        }

        /**
         * 获取 principal
         * 
         * @return principal principal
         */
        public AuthPrincipal getPrincipal() {
            return principal;
        }

        /**
         * 设置 principal
         * 
         * @param principal principal
         */
        public void setPrincipal(AuthPrincipal principal) {
            this.principal = principal;
        }

        /**
         * 获取 servletRequestWrapper
         * 
         * @return servletRequestWrapper servletRequestWrapper
         */
        public ServletRequest getServletRequestWrapper() {
            return servletRequestWrapper;
        }

        /**
         * 设置 servletRequestWrapper
         * 
         * @param servletRequestWrapper servletRequestWrapper
         */
        public void setServletRequestWrapper(ServletRequest servletRequestWrapper) {
            this.servletRequestWrapper = servletRequestWrapper;
        }

    }

}
