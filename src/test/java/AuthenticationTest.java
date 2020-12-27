import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.ShiroDemoApplication;
import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShiroDemoApplication.class)
public class AuthenticationTest {

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void insertSomething() {
        SysUser user = new SysUser();
        String salt = MD5Util.generateSalt();
        user.setUsername("xiaoweng");
        user.setSalt(salt);
        user.setPassword(MD5Util.getMd5("111111", salt));
        user.setStatus(1);
        user.setEmail("xiaoweng@gmail.com");

        userMapper.insert(user);

    }

}
