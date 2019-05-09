/**
 * Copyright (C), 2011-2017, org.gy.sample
 * ProjectName:	jhd-web
 * FileName: AuthFilter.java
 *
 * @Author gy
 * @Date 2017年7月4日下午10:51:31
 */
package org.gy.framework.util.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.AuthHandler.AuthResult;
import org.gy.framework.util.auth.config.Configuration;
import org.gy.framework.util.auth.config.Configuration.AccessPolicy;

/**
 * 功能描述：
 * 
 * @Author gy
 * @Date 2017年7月4日下午10:51:31
 */
public class AuthFilter implements Filter {

    private static final String AUTH_STATUS_URL_PATTERN_PARAM   = "authStatusUrlPattern";

    private static final String DEFAULT_AUTH_STATUS_URL_PATTERN = "/authStatus";

    private List<String>        ignorePatternList               = new ArrayList<>();

    private ServletContext      servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        Configuration.initialize(this.servletContext);

        String authStatusServletUrlPattern = filterConfig.getInitParameter(AUTH_STATUS_URL_PATTERN_PARAM);
        if (StringUtils.isBlank(authStatusServletUrlPattern)) {
            authStatusServletUrlPattern = DEFAULT_AUTH_STATUS_URL_PATTERN;
        }
        ignorePatternList.add(authStatusServletUrlPattern);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        response.setDateHeader("Expires", 0L);

        String requestUri = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (StringUtils.isNotBlank(pathInfo)) {
            requestUri += pathInfo;
        }

        if (Configuration.matching(requestUri, ignorePatternList)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        Configuration configuration = Configuration.getInstance(this.servletContext);
        AccessPolicy policy = configuration.matchAccessPolicy(requestUri);

        handler(request, response, configuration, policy, chain);

    }

    private void handler(HttpServletRequest request,
                         HttpServletResponse response,
                         Configuration configuration,
                         AccessPolicy policy,
                         FilterChain chain) throws IOException, ServletException {
        if (AccessPolicy.RESTRICTED.equals(policy)) {
            AuthResult result = AuthHandler.handler(request, response, configuration);
            if (!result.isAuthStatus()) {
                AuthHandler.redirectHandler(request, response, configuration, policy);
                return;
            }
            ServletRequest wrapper = result.getServletRequestWrapper();
            chain.doFilter(wrapper == null ? request : (HttpServletRequest) wrapper, response);
            return;
        } else if (AccessPolicy.GATEWAY.equals(policy)) {
            AuthResult result = AuthHandler.handler(request, response, configuration);
            ServletRequest wrapper = result.getServletRequestWrapper();
            chain.doFilter(wrapper == null ? request : (HttpServletRequest) wrapper, response);
            return;
        } else {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {
        //
    }

}
