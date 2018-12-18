package com.firesoon.idaweb.shiro.cas.realm;

import com.firesoon.dto.user.User;
import com.firesoon.idaservice.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 16:55
 */
@Slf4j
public class MyShiroCasRealm extends CasRealm {
    @Autowired
    private UserService userSerivce;


    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see ：本例中该方法的调用时机为需授权资源被访问时
     * @see ：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see ：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.info("权限验证成功");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        AuthenticationInfo authc = null;
        try {
            authc = super.doGetAuthenticationInfo(token);
            if (authc != null) {
                String account = (String) authc.getPrincipals().getPrimaryPrincipal();
                if (account != null) {
                    //保存当前用户信息
                    User user = userSerivce.findUserByLogin(account);
                    Map<String, Object> result = userSerivce.userDepts(user);
                    userSerivce.setDept(result, user);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return authc;
    }
}