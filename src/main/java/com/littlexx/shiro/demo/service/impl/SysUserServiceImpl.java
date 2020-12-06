package com.littlexx.shiro.demo.service.impl;

import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author littlexx
 * @since 2020-11-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
