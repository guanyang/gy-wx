package org.gy.framework.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.gy.framework.model.SysUser;

public class UserSessionUtil {

    /**
     * 功能描述: 获取登录用户会话session
     * 
     * @return
     */
    public static Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 功能描述: 获取登录用户信息
     * 
     * @return
     */
    public static SysUser getCurrentSysUser() {
        return (SysUser) getCurrentSession().getAttribute(ConstantUtil.SYS_USER_SESSION_KEY);
    }

    /**
     * 功能描述: 获取登录用户编码
     * 
     * @return
     */
    public static Long getCurrentSysUserId() {
        return (Long) getCurrentSession().getAttribute(ConstantUtil.SYS_USER_SESSION_ID_KEY);
    }

}
