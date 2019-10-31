package com.h5.controller;


import com.h5.entity.User;
import com.h5.entity.UserVO;
import com.h5.entity.response.AppResponse;
import com.h5.redis.RedisUtils;
import com.h5.service.IUserService;
import com.h5.utils.CreateMD5;
import com.h5.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
@Api("用户管理")
@Transactional(readOnly = false)
public class UserController {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private IUserService userService;

    @Value("${loginTimeOut}")
    private Integer loginTimeOut;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @Description：用户注册
     * @Date:2019/9/26
     * @Param:User
     */
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "registered")
    @Transactional(rollbackFor = Exception.class)
    public AppResponse registered(String userName , String userPassword) throws UnsupportedEncodingException {
        User user =new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        HashMap<String , Object> map = new HashMap<>();
        map.put("userName" , user.getUserName());
        List<User> list = (List<User>) userService.listByMap(map);
        if (list != null && list.size() != 0){
            return AppResponse.bizError("用户名已存在！");
        }else {
            Random random = new Random();
            user.setId(UUIDUtil.uuidStr());
            user.setUserPassword(CreateMD5.getMd5(user.getUserPassword()));
            user.setUserNick("用户_"+((int)random.nextDouble()*(99999-10000+1))+10000);
            user.setCreateBy(user.getId());
            user.setGmtCreate(df.format(new Date()));
            user.setGmtModified(df.format(new Date()));
            user.setIsDelete(0);
            user.setLastModifiedBy(user.getId());
            user.setSortNo(1);
            user.setVersion(0);
            boolean save = userService.save(user);
            if (save){
                return AppResponse.success("注册成功！");
            }
            return AppResponse.bizError("注册失败，请重试！");
        }
    }

    /**
     * @Description：用户登陆
     * @Date:2019/9/26
     * @Param:UserVO
     */
    @ApiOperation(value = "用户登陆")
    @PostMapping(value = "userLogin")
    @Transactional(readOnly = true)
    public AppResponse userLogin(String userName , String userPassword) throws UnsupportedEncodingException {
        UserVO user = new UserVO();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        HashMap<String , Object> map = new HashMap<>();
        map.put("userName" , user.getUserName());
        map.put("userPassword" , CreateMD5.getMd5(user.getUserPassword()));
        UserVO uv = new UserVO();
        List<User> list = (List<User>) userService.listByMap(map);
        String  token = "";
        if (list != null && 1 == list.size()){
            token = UUIDUtil.uuidStr();
            redisUtils.LoginSet(token+"_userID",list.get(0).getId(),loginTimeOut);
            redisUtils.LoginSet(token+"_userName",list.get(0).getUserName(),loginTimeOut);
            if (list.get(0).getUserNick() != null){
                redisUtils.LoginSet(token+"_userNick",list.get(0).getUserNick(),loginTimeOut);
            }
            uv.setUser(list.get(0));
            uv.setToken(token);
//            redisUtils.LoginSet(token+"version",list.get(0).getVersion(),loginTimeOut);
        }else {
            return AppResponse.bizError("登陆失败,请重试！");
        }
//        List<UserVO> list1 = (List<UserVO>) list.get(0);
//        list1.get(0).setToken(token);
       // System.out.println(uv);
        return AppResponse.success("登陆成功！" , uv);
    }

    /**
     * @Description：获取用户信息
     * @Date:2019/9/26
     * @Param:
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "getUser")
    @Transactional(readOnly = true)
    public User getUser(String token){
        UserVO user = new UserVO();
        user.setToken(token);
        User user1 = new User();
        user1.setId(redisUtils.get(user.getToken()+"_userID"));
        user1.setUserName(redisUtils.get(user.getToken()+"_userName"));
        user1.setUserNick(redisUtils.get(user.getToken()+"_userNick"));
//        System.out.println(user1);
        return user1;
    }

    /**
     * @Description：修改密码
     * @Date:2019/9/26
     * @Param:UserVO
     */
    @ApiOperation(value = "修改密码")
    @PostMapping(value = "updatePassWord")
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updatePassWord(String userPassword,String userNewPassword , String token) throws UnsupportedEncodingException {
        UserVO user = new UserVO();
        user.setToken(token);
        String id = redisUtils.get(user.getToken()+"_userID");
        user.setId(id);
        user.setUserPassword(userPassword);
        user.setUserNewPassword(userNewPassword);
        user.setUserPassword(CreateMD5.getMd5(user.getUserPassword()));
        User user1 = userService.getById(user);
        if (user.getUserPassword().equals(user1.getUserPassword())){
            user1.setVersion(user1.getVersion()+1);
            user1.setUserPassword(CreateMD5.getMd5(user.getUserNewPassword()));
            user1.setGmtModified(df.format(new Date()));
            user1.setLastModifiedBy(user.getId());
            userService.updateById(user1);
            return AppResponse.success("修改成功！");
        }else {
            return AppResponse.bizError("修改失败，请重试！");
        }
    }

    /**
     * @Description：修改昵称
     * @Date:2019/9/26
     * @Param:UserVO
     */
    @ApiOperation(value = "修改昵称")
    @PostMapping(value ="updateNick")
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateNick(String userNick , String token){
        UserVO user = new UserVO();
        user.setToken(token);
        user.setUserNick(userNick);
        String id = redisUtils.get(user.getToken()+"_userID");
        System.out.println(id);
        user.setId(id);
        user.setLastModifiedBy(id);
//        user.setLastModifiedBy("2960bd6730064ca9a64059693162ba47");
        user.setGmtModified(df.format(new Date()));
        user.setVersion(user.getVersion()+1);
        boolean update =userService.updateById(user);
        if (update){
            return  AppResponse.success("完善成功！");
        }
        return AppResponse.bizError("完善失败，请重试！");
    }
}
