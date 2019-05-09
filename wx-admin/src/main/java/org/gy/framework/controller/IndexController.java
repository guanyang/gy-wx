package org.gy.framework.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.gy.framework.biz.SysUserBiz;
import org.gy.framework.model.SysUser;
import org.gy.framework.util.PasswordHelper;
import org.gy.framework.util.UserSessionUtil;
import org.gy.framework.util.response.Response;
import org.gy.framework.util.response.Result;
import org.gy.framework.util.response.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    public static final String DEFAULT_LOGIN             = "admin/login.ftl";

    public static final String DEFAULT_TARGET_URL_PARAM  = "targetUrl";
    public static final String DEFAULT_LOGOUT_TARGET_URL = "/login.htm";
    public static final String DEFAULT_LOGIN_TARGET_URL  = "/index.htm";

    @Autowired
    private SysUserBiz         sysUserBiz;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("admin/index.ftl");
        SysUser user = UserSessionUtil.getCurrentSysUser();
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView toLogin() {
        return new ModelAndView(DEFAULT_LOGIN);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(String username,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        Result result = new Result();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            result.wrapResultCode(ReturnCode.E1001);
            ajaxJson(request, response, result);
            return;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // token.setRememberMe(true);//记住我操作
        try {
            // 获取当前的Subject
            Subject currentUser = SecurityUtils.getSubject();
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
            // 验证是否登录成功
            if (currentUser.isAuthenticated()) {
                // 这里可以进行一些认证通过后的一些系统参数初始化操作
                // WebUtils.redirectToSavedRequest(request, response, DEFAULT_LOGIN_TARGET_URL);//重定向
                String successUrl = wrapSuccessUrl(request);
                result.addObject(DEFAULT_TARGET_URL_PARAM, successUrl);
            } else {
                token.clear();// 清除信息
                result.wrapResultCode(ReturnCode.E9999);
            }
        } catch (LockedAccountException lae) {
            logger.warn("您的账户" + username + "已被锁定", lae);
            result.wrapResultCode(ReturnCode.E1003, username);
        } catch (ExcessiveAttemptsException eae) {
            logger.warn("账号" + username + "登录失败次数过多,锁定1小时！", eae);
            result.wrapResultCode(ReturnCode.E1004, username);
        } catch (AuthenticationException ae) {
            // UnknownAccountException账号不存在
            // IncorrectCredentialsException密码不正确
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.warn("用户名或密码不正确，username=" + username + "，password=" + password, ae);
            result.wrapResultCode(ReturnCode.E1002);
        } catch (Exception e) {
            logger.warn("登录异常，username=" + username + "，password=" + password, e);
            result.wrapResultCode(ReturnCode.E9999);
        }
        ajaxJson(request, response, result);
    }

    /**
     * 功能描述:组装登录成功跳转url
     * 
     * @param request
     * @return
     */
    private String wrapSuccessUrl(HttpServletRequest request) {
        String successUrl = null;
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
            successUrl = savedRequest.getRequestUrl();
            String contextPath = WebUtils.getContextPath(request);
            if (StringUtils.startsWithIgnoreCase(successUrl, contextPath)) {
                successUrl = successUrl.substring(contextPath.length());
            }
        }
        if (StringUtils.isBlank(successUrl)) {
            successUrl = DEFAULT_LOGIN_TARGET_URL;
        }
        return successUrl;
    }

    /**
     * 用户登出
     * 
     * @throws IOException
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String targetUrl = request.getParameter(DEFAULT_TARGET_URL_PARAM);
        if (StringUtils.isBlank(targetUrl)) {
            targetUrl = DEFAULT_LOGOUT_TARGET_URL;
        }
        // 使用权限管理工具进行用户的退出，注销登录
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
            logger.error(ise.getMessage(), ise);
        }
        WebUtils.issueRedirect(request, response, targetUrl);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(String oldPassword,
                                       String newPassword) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        Response result = new Response();
        result.setSuccess(false);
        mav.addObject("response", result);
        result.setSuccess(false);
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            result.setMessage("参数有误，请重新输入");
            return mav;
        }
        SysUser user = UserSessionUtil.getCurrentSysUser();
        boolean flag = PasswordHelper.validateEqual(oldPassword, user.getSalt(), user.getPassword());
        if (!flag) {
            result.setMessage("原密码不正确，请重新输入");
            return mav;
        }
        String salt = PasswordHelper.generateSalt();// 生成新的盐
        user.setSalt(salt);
        user.setPassword(PasswordHelper.generatePassword(newPassword, salt));
        sysUserBiz.updateByPrimaryKeySelective(user);
        result.setSuccess(true);
        result.setMessage("修改成功，请用新密码重新登录");
        return mav;
    }
}
