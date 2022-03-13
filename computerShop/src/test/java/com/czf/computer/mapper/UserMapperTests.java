package com.czf.computer.mapper;

import com.czf.computer.domain.User;
import com.czf.computer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest    //表明是测试类，不会被打包
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    //单元测试方法public void xx（），且必须有@Test
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("老肥");
        user.setPassword("123456");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("老肥");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        //void changePassword(int uid, String username, String oldPassword, String newPassword)
        userService.changePassword(6,"123456","123456","123456");
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(4);
        user.setEmail("12345678@qq.com");
        user.setPhone("18565345955");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }


}
