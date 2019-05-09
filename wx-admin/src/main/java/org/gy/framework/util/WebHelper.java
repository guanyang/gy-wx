package org.gy.framework.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gy.framework.util.json.JacksonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebHelper {

    protected final static Logger logger                      = LoggerFactory.getLogger(WebHelper.class);

    public static final String    DEFAULT_AJAX_REQUEST        = "XMLHttpRequest";

    public static final String    DEFAULT_AJAX_REQUEST_HEADER = "X-Requested-With";

    /**
     * 功能描述: 验证是否是ajax请求
     * 
     */
    public static boolean validateAjaxRequest(HttpServletRequest request) {
        return DEFAULT_AJAX_REQUEST.equalsIgnoreCase(request.getHeader(DEFAULT_AJAX_REQUEST_HEADER));
    }

    /**
     * 功能描述: 响应json信息
     * 
     */
    @SuppressWarnings("rawtypes")
    public static void responseJsonMessage(HttpServletResponse response, ResponseVO vo) {
        PrintWriter writer = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(JacksonMapper.beanToJson(vo));
            writer.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                writer.close();
            }

        }
    }

}
