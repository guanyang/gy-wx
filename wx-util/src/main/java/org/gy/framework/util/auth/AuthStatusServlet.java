package org.gy.framework.util.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gy.framework.util.auth.AuthHandler.AuthResult;
import org.gy.framework.util.auth.config.Configuration;

public class AuthStatusServlet extends HttpServlet {

    private static final long serialVersionUID = -6172077102770142677L;

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/javascript;charset=UTF-8");
        String callback = request.getParameter(Constants.CALLBACK_PARAM);

        Configuration configuration = Configuration.getInstance(getServletContext());
        AuthResult authResult = AuthHandler.handler(request, response, configuration);
        ServletRequest wrapper = authResult.getServletRequestWrapper();
        HttpServletRequest newRequest = (wrapper == null) ? request : (HttpServletRequest) wrapper;

        StringBuilder result = new StringBuilder();

        String remoteUser = newRequest.getRemoteUser();
        boolean hasLogin = remoteUser != null && !"".equals(remoteUser.trim());
        StringBuilder status = new StringBuilder();
        status.append("{\"authStatus\":").append(hasLogin);
        if (hasLogin) {
            status.append(",\"principal\":\"").append(remoteUser).append("\"}");
        } else {
            status.append("}");
        }

        if (callback == null || "".equals(callback.trim())) {
            result.append(status);
        } else {
            result.append(callback).append("(").append(status).append(")");
        }
        response.getWriter().write(result.toString());
        response.getWriter().flush();
    }
}
