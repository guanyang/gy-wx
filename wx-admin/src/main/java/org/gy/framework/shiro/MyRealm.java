package org.gy.framework.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.gy.framework.biz.SysUserBiz;
import org.gy.framework.model.SysUser;
import org.gy.framework.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm,进行数据源配置
 * 
 * @author lanyuan 2014-12-25
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysUserBiz sysUserBiz;

    /**
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证回调函数,登录时调用 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
     * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
     * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配， 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
     * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException； 在组装SimpleAuthenticationInfo信息时，
     * 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt）， CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        SysUser sysUser = sysUserBiz.findSysUserByAccountName(username);
        if (sysUser != null) {
            if (sysUser.getStatus() == 0) {
                throw new LockedAccountException(); // 帐号锁定
            }
            // 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
            // 当用户执行登录时,在方法处理上要实现user.login(token);
            // 然后会自动进入这个类进行认证
            // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
                    sysUser.getPassword(), // 密码
                    ByteSource.Util.bytes(sysUser.getSalt()),// salt
                    getName() // realm name
            );
            // 当验证都通过后，把用户信息放在session里
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(ConstantUtil.SYS_USER_SESSION_KEY, sysUser);
            session.setAttribute(ConstantUtil.SYS_USER_SESSION_ID_KEY, sysUser.getId());
            return authenticationInfo;
        } else {
            throw new UnknownAccountException();// 没找到帐号
        }

    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 更新用户信息缓存.
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 清空所有缓存
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}