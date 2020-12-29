package com.littlexx.shiro.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.model.SysRole;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.security.cache.IRedisTokenCacheService;
import com.littlexx.shiro.demo.service.ISysRoleService;
import com.littlexx.shiro.demo.service.ISysUserService;
import com.littlexx.shiro.demo.utils.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IRedisTokenCacheService redisTokenCacheService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String jwtToken = (String) principalCollection.getPrimaryPrincipal();
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<SysRole> sysRoles = roleService.getSysRolesByUsername(username);
        if (!sysRoles.isEmpty()) {
            Set<String> sysRoleNames = new HashSet<>();
            Set<String> sysRolePermissions = new HashSet<>();
            for (SysRole sysRole : sysRoles) {
                sysRoleNames.add(sysRole.getName());
                sysRolePermissions.add(sysRole.getPermissionCode());
            }
            // 设置角色
            authorizationInfo.setRoles(sysRoleNames);
            // 设置权限
            authorizationInfo.setStringPermissions(sysRolePermissions);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new AuthenticationException("token expired");
        }

        // 判断redis中是否有该token
        if (redisTokenCacheService.tokenCached(username, token)) {
            return new SimpleAuthenticationInfo(token, token, "my_realm");
        }

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = userService.getOne(wrapper);
        if (sysUser == null) {
            throw new AuthenticationException("User didn't exist");
        }
        if (!jwtTokenUtil.validateToken(token, sysUser.getUsername(), sysUser.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
