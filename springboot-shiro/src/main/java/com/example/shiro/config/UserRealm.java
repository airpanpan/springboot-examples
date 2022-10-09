package com.example.shiro.config;

import com.example.shiro.entity.Role;
import com.example.shiro.entity.RolePermisson;
import com.example.shiro.entity.User;
import com.example.shiro.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        IUserService userService = (IUserService)ApplicationContextUtil.getBean(IUserService.class);
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        User user = userService.loadUserByUsername(primaryPrincipal.toString());
        List<Role> roles = userService.queryRolesByUserId(user.getId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (CollectionUtils.isEmpty(roles)){
            return null;
        }

        roles.forEach(c -> {
            simpleAuthorizationInfo.addRole(c.getName());

           /* List<RolePermisson> rolePermissons = userService.queryPermsByRoleId(c.getName());
            if (!CollectionUtils.isEmpty(rolePermissons)){
                rolePermissons.forEach(b -> {
                    simpleAuthorizationInfo.addStringPermission(b.getUrl());
                });
            }*/

        });

        return simpleAuthorizationInfo;
       // return  null;
    }

    /**
     * 获取认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Object principal = authenticationToken.getPrincipal();
        /*if (principal.equals("zzq")){
            return new SimpleAuthenticationInfo(principal, "12345", this.getName());
        }*/
        IUserService userService = (IUserService)ApplicationContextUtil.getBean(IUserService.class);
        User user = userService.loadUserByUsername(principal.toString());

        /*
           普通登录，不加密
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), this.getName());*/
        /**
         * 加密登录 采用md5
         */
        ByteSource slat = ByteSource.Util.bytes(user.getUsername());
        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), slat, this.getName());
    }
}
