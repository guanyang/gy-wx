package org.gy.framework.util.monitor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonitorServlet extends HttpServlet {

    private static final long   serialVersionUID    = -2385711000408690071L;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String        startup;
    public static String        version;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();
        result.append("startup=");
        result.append(startup);
        result.append(" | version=");
        result.append(version);
        resp.getWriter().write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 启动时间
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        startup = sdf.format(new Date());
        // 服务版本号，从web.xml中读取version信息，需要配置init-param参数
        version = config.getInitParameter("version");
    }

}
