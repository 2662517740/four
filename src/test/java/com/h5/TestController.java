package com.h5;

import com.h5.entity.User;
import com.h5.entity.UserVO;
import com.h5.redis.RedisUtils;
import com.h5.service.IUserService;
import com.h5.utils.CreateMD5;
import com.h5.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = {test.class})
@RunWith(SpringRunner.class)
public class TestController {

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private IUserService userService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 用户注册
     */
    @Test
    public void testRegistered() throws UnsupportedEncodingException {
        User user = new User();
        user.setUserName("demo");
        HashMap<String , Object> map = new HashMap<>();
        map.put("userName" , user.getUserName());
        List<User> list = (List<User>) userService.listByMap(map);
        if (list != null && list.size() != 0){
            System.out.print(list);
            System.out.print("用户名已存在！");
        }else {
            user.setId(UUIDUtil.uuidStr());
            user.setUserPassword(CreateMD5.getMd5("123456"));
            user.setCreateBy(user.getId());
            user.setGmtCreate(df.format(new Date()));
            user.setGmtModified(df.format(new Date()));
            user.setIsDelete(0);
            user.setLastModifiedBy(user.getId());
            user.setSortNo(1);
            user.setVersion(0);
            userService.save(user);
            System.out.print(user);
        }

    }

    /**
     * 用户登陆
     */
    @Test
    public void userLogin() throws UnsupportedEncodingException {
        HashMap<String , Object> map = new HashMap<>();
        map.put("userName" , "demo");
        map.put("userPassword" , CreateMD5.getMd5("123456"));
        List<User> list = (List<User>) userService.listByMap(map);
        System.out.print(list);
    }

    /**
     * 修改密码
     */
    @Test
    public void updatePassWord() throws UnsupportedEncodingException {
        UserVO user = new UserVO();
        user.setId("4fa3bd76a89946a09a1e1bba85413a01");
        user.setUserPassword(CreateMD5.getMd5("123456"));
        user.setUserNewPassword(CreateMD5.getMd5("654321"));
        User user1 = userService.getById(user);
        if (user1.getUserPassword().equals(user.getUserPassword())){
            System.out.print(user1.getUserName());
        }
        user1.setVersion(user1.getVersion()+1);
        user1.setUserPassword(user.getUserNewPassword());
        user1.setGmtModified(df.format(new Date()));
        user1.setLastModifiedBy(user.getId());
        userService.updateById(user1);
    }

    /**
     * 修改昵称
     */
    @Test
    public void updateNick(){
        User user = new User();
        user.setId("31112f5c840f42ad97e7e34a542e01b2");
        user.setUserNick("天王凉破");
        userService.updateById(user);
    }
}
