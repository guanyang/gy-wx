package org.gy.framework.util.auth.wx;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.response.ResponseUtil;
import org.gy.framework.util.response.Result;
import org.gy.framework.util.response.ReturnCode;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class WxAuthServlet extends HttpServlet {

    private static final long  serialVersionUID   = -5810053381906289014L;

    public static final String WX_AUTH_CODE_PARAM = "code";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        response.setDateHeader("Expires", 0L);

        Result result = new Result();
        String code = request.getParameter(WX_AUTH_CODE_PARAM);
        if (StringUtils.isNotBlank(code)) {
            result.addObject("code", code);
        } else {
            result.wrapResultCode(ReturnCode.E9998, "授权code为空");
        }
        ResponseUtil.ajaxJson(request, response, result);

    }

}
