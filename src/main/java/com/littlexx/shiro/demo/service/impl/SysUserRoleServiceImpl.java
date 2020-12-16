package com.littlexx.shiro.demo.service.impl;

import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.model.SysRole;
import com.littlexx.shiro.demo.model.SysUserRole;
import com.littlexx.shiro.demo.dao.SysUserRoleMapper;
import com.littlexx.shiro.demo.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色对应表 服务实现类
 * </p>
 *
 * @author littlexx
 * @since 2020-11-28
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserMapper userMapper;

}
