package com.littlexx.shiro.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.dao.SysUserRoleMapper;
import com.littlexx.shiro.demo.model.SysRole;
import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.model.SysUserRole;
import com.littlexx.shiro.demo.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author littlexx
 * @since 2020-11-28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Set<SysRole> getSysRolesByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sysUser.getId());
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);
        if (sysUserRoles.isEmpty()) return null;
        Set<SysRole> sysRoles = new HashSet<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            SysRole sysRole = sysRoleMapper.selectById(sysUserRole.getRoleId());
            sysRoles.add(sysRole);
        }
        return sysRoles;
    }
}
