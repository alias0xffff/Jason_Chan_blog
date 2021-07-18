package com.jasonc.blog.realm;

import com.jasonc.blog.entity.User;
import com.jasonc.blog.service.UserService;
import com.jasonc.blog.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.realm
 * @ClassName: UserRealm
 * @Author: Jason Chan
 * @Description:
 */
public class UserRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author Jason Chan
     * @Description 这个是当登陆成功之后会被调用，看当前的登陆角色是有有权限来进行操作
     * @Param [principalCollection]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("message : {} ", "开始授权");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String name = principalCollection.getPrimaryPrincipal().toString();
        // User user = userService.getUser(name);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpSession httpSession = requestAttributes.getRequest().getSession();
        User user = (User) httpSession.getAttribute("user");
        if (user.getType() == 1) {
            simpleAuthorizationInfo.addRole("admin");
        }
        logger.info("message : {}", "授权完毕");
        return simpleAuthorizationInfo;
    }

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author Jason Chan
     * @Description 登录认证
     * @Param [authenticationToken]
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("message : {}", "进行认证");
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
//        根据前端传的username查询数据库
        String loginUsername = authenticationToken.getPrincipal().toString();
        // 数据库中的真实user
        User user = userService.getUser(loginUsername);
        if (user == null) {
            // 会到LoginController层报出UnknownAccountException用户名不存在异常
            return null;
        } else {
            // 认证
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginUsername, user.getPassword(), this.getName());
//          若认证通过，将user加入httpSession
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpSession httpSession = servletRequestAttributes.getRequest().getSession();
            // 保证密码安全
            user.setPassword(null);
            httpSession.setAttribute("user", user);
            logger.info("message : {}", "结束认证");
            return simpleAuthenticationInfo;
        }
    }
}
