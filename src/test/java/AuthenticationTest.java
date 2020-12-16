import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.littlexx.shiro.demo.ShiroDemoApplication;
import com.littlexx.shiro.demo.dao.SysRoleMapper;
import com.littlexx.shiro.demo.dao.SysUserMapper;
import com.littlexx.shiro.demo.model.SysUser;
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

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("tester", "123456");
    }

    @Test
    public void testAuthentication() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("tester", "123456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
    }

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void insertSomething() {
        SysUser user = new SysUser();
        user.setUsername("admin");
        user.setPassword("111111");
        user.setStatus(1);

        userMapper.insert(user);

    }


}
