package com.littlexx.shiro.demo.service.impl;

import com.littlexx.shiro.demo.model.SysRole;
import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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

}
