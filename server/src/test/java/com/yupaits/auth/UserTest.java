package com.yupaits.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupaits.auth.entity.User;
import com.yupaits.auth.service.IUserService;
import com.yupaits.commons.consts.SecurityConsts;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yupaits
 * @date 2018/10/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testAddUser() {
        User user = new User().setUsername("admin")
                .setPassword(new Sha256Hash("123456", "admin" + SecurityConsts.CREDENTIALS_SALT, SecurityConsts.ITERATIONS).toHex())
                .setEnabled(true);
        Assert.assertTrue(userService.save(user));
    }

    @Test
    public void testDeleteUser() {
        Assert.assertTrue(userService.remove(new QueryWrapper<User>().eq("username", "admin")));
    }
}
