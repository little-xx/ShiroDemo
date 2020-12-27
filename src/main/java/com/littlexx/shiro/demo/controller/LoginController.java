package com.littlexx.shiro.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.model.dto.LoginBodyDto;
import com.littlexx.shiro.demo.service.ISysUserService;
import com.littlexx.shiro.demo.tips.ErrorTip;
import com.littlexx.shiro.demo.tips.SuccessTip;
import com.littlexx.shiro.demo.tips.Tip;
import com.littlexx.shiro.demo.utils.JwtTokenUtil;
import com.littlexx.shiro.demo.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author littlexx
 * @since 2020-11-28
 */
@RestController
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public Tip login(@RequestBody LoginBodyDto loginBodyDto) {
        try {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("username", loginBodyDto.getUsername());
            SysUser sysUser = sysUserService.getOne(wrapper);
            if (Objects.isNull(sysUser)) return new ErrorTip("没有该用户");
            String md5 = MD5Util.getMd5(loginBodyDto.getPassword(), sysUser.getSalt());
            if (!StringUtils.equals(sysUser.getPassword(), md5)) return new ErrorTip("密码错误");
            String token = jwtTokenUtil.generateToken(sysUser.getUsername(), sysUser.getPassword(), sysUser.getId());
            return new SuccessTip("登录成功", token);
        } catch (Exception e) {
            return new ErrorTip(e.getMessage());
        }
    }

    @GetMapping("/getMessage")
    public Tip test(){
        try {
            return new SuccessTip("haha");
        } catch (Exception e) {
            return new ErrorTip(e.getMessage());
        }

    }
}
