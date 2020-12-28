import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.ShiroDemoApplication;
import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.dao.SysUserRoleMapper;
import com.littlexx.shiro.demo.model.SysRole;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.model.SysUserRole;
import com.littlexx.shiro.demo.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShiroDemoApplication.class)
public class AuthenticationTest {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void insertSomething() {
        SysUser user = new SysUser();
        String salt = MD5Util.generateSalt();
        user.setUsername("xiaotang");
        user.setSalt(salt);
        user.setPassword(MD5Util.getMd5("222222", salt));
        user.setStatus(1);
        user.setEmail("xiaowtang@gmail.com");

        userMapper.insert(user);

    }

    @Test
    public void testRedis() {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", "1343565253299986434");
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);
        for (SysUserRole sysUserRole : sysUserRoles) {
            System.out.println(sysUserRole);
        }
    }

}
