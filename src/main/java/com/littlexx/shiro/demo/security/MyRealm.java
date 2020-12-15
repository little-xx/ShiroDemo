package com.littlexx.shiro.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.service.ISysUserService;
import com.littlexx.shiro.demo.utils.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthenticatingRealm {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
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
