package com.littlexx.shiro.demo.dao;

import com.littlexx.shiro.demo.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author littlexx
 * @since 2020-11-28
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
