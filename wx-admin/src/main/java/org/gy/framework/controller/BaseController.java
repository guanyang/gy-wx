package org.gy.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.generator.Pagination;
import org.gy.framework.util.response.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ModelAndView errorView() {
        return new ModelAndView("redirect:/error.html");
    }

    /**
     * 功能描述: 初始化分页
     * 
     * @param query
     * @param request
     */
    public void initQuery(Pagination query,
                          HttpServletRequest request) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int pageNo = 1;
        int pageSize = 10;
        if (StringUtils.isNumeric(page)) {
            pageNo = Integer.parseInt(page);
        }
        if (StringUtils.isNumeric(rows)) {
            pageSize = Integer.parseInt(rows);
        }
        query.setPageNo(pageNo);
        query.setPageSize(pageSize);
    }
    
    /**
     * 功能描述: json输出 <br>
     * <li>入参为封装对象</li><br>
     * <li>有callback，则jsonp输出，没有则json输出</li>
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @param object 待输出对象
     */
    public static void ajaxJson(HttpServletRequest request,
                                HttpServletResponse response,
                                Object object) {
        ResponseUtil.ajaxJson(request, response, object);
    }

}
