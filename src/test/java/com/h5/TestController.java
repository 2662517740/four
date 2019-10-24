package com.h5;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.h5.entity.Score;
import com.h5.entity.Snake;
import com.h5.entity.User;
import com.h5.entity.UserVO;
import com.h5.redis.RedisUtils;
import com.h5.service.IScoreService;
import com.h5.service.ISnakeService;
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

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;


@SpringBootTest(classes = {test.class})
@RunWith(SpringRunner.class)
public class TestController {

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private ISnakeService snakeService;

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

    /**
     * 创建蛇
     */
    @Test
    public void setSnake(){
        Snake snake = new Snake();
        snake.setId(UUIDUtil.uuidStr());
        snake.setSnakeColor("blue");
        snake.setSnakeSkin("喜羊羊");
        snake.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
        snake.setGmtCreate(df.format(new Date()));
        snake.setGmtModified(df.format(new Date()));
        snake.setIsDelete(0);
        snake.setLastModifiedBy("31112f5c840f42ad97e7e34a542e01b2");
        snake.setVersion(0);
        snake.setSortNo(0);
        snakeService.save(snake);
        System.out.print(snake);
    }

    /**
     * 修改蛇的颜色
     */
    @Test
    public void updateSnakeColor(){
        Snake snake = new Snake();
        snake.setId("03399e2e555549ce95fac7e0bdaee828");
        snake.setSnakeColor("red");
        snake.setGmtModified(df.format(new Date()));
        snake.setLastModifiedBy("31112f5c840f42ad97e7e34a542e01b2");
        snake.setVersion(1);
        snakeService.updateById(snake);
    }

    /**
     * 蛇列表
     */
    @Test
    public void getSnake(){
        Snake snake= new Snake();
        snake.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
        IPage<Snake> iPage = new Page<>();
        iPage.setPages(0);
        iPage.setCurrent(0L);
        IPage<Snake> snakeIPage = snakeService.page(iPage , new QueryWrapper<Snake>().eq("create_by" ,snake.getCreateBy()));
        System.out.println(snakeIPage);
    }

    /**
     * 修改蛇历史长度
     */
    @Test
    public void updateLength(){
        Snake snake = new Snake();
        snake.setSnakeLength(123);
        snake.setId("03399e2e555549ce95fac7e0bdaee828");
        Snake snake1 = snakeService.getById(snake);
        if (snake1.getSnakeLength() < snake.getSnakeLength()){
            snake.setVersion(snake1.getVersion()+1);
            snake.setLastModifiedBy(snake.getId());
            snake.setGmtModified(df.format(new Date()));
            snakeService.updateById(snake);
        }
        System.out.println(snakeService.getById(snake));
    }

    /**
     * 查询蛇的最大长度
     */
    @Test
    public void bestLength(){
        Snake snake = new Snake();
        snake.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
        IPage<Snake> iPage = new Page<>();
        iPage.setCurrent(0L);
        iPage.setPages(0);
        IPage<Snake> snakeIPage = snakeService.page(iPage ,new QueryWrapper<Snake>().eq("Create_by" , snake.getCreateBy()).orderByDesc("snakeLength"));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println(snakeIPage.getRecords().size());
        System.out.println(snakeIPage.getRecords().get(0));
    }

    /**
     * 创建分数
     */
    @Test
    public void setScore(){
        Score score = new Score();
        score.setUserId("4fa3bd76a89946a09a1e1bba85413a01");
        score.setScoreCheckpoint(8);
        score.setScoreDifficulty("低");
        score.setScoreSC("789000");
        HashMap<String , Object> map = new HashMap<>();
        map.put("userId" , score.getUserId());
        map.put("scoreCheckpoint" , score.getScoreCheckpoint());
        map.put("scoreDifficulty",score.getScoreDifficulty());
        List<Score> list = (List<Score>) scoreService.listByMap(map);
        if (list != null && list.size()!=0){
            if ( list.get(0).getScoreSC().compareTo(score.getScoreSC()) < 0 ){
                score.setId(list.get(0).getId());
                score.setGmtModified(df.format(new Date()));
                score.setIsDelete(0);
                score.setLastModifiedBy("4fa3bd76a89946a09a1e1bba85413a01");
                score.setVersion(1);
                score.setSortNo(0);
                scoreService.updateById(score);
            }else {
                System.out.print("已存在");
            }
        }else {
            score.setId(UUIDUtil.uuidStr());
            score.setIs_pass(1);
//            score.setScoreCheckpoint(1);
//            score.setScoreDifficulty("低");
//            score.setScoreSC("1100");
            score.setSnakeId("156418543212");
//            score.setUserId("31112f5c840f42ad97e7e34a542e01b2");
            score.setCreateBy("大佬");
            score.setGmtCreate(df.format(new Date()));
            score.setGmtModified(df.format(new Date()));
            score.setIsDelete(0);
            score.setLastModifiedBy("4fa3bd76a89946a09a1e1bba85413a01");
            score.setVersion(0);
            score.setSortNo(0);
            scoreService.save(score);
            System.out.println(score);
        }

    }

    /**
     *排行榜
     */
    @Test
    public void getScoreList(){
        Score score = new Score();
        score.setScoreDifficulty("低");
        IPage<Score> iPage = new Page<>();
        iPage.setPages(0);
        iPage.setCurrent(0L);
        IPage<Score> scoreIPage = scoreService.page(iPage , new QueryWrapper<Score>().eq("scoreDifficulty",score.getScoreDifficulty()).orderByDesc("scoreSC"));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println(scoreIPage.getRecords().size());
        System.out.println(scoreIPage.getRecords());
    }

    /**
     * 获取分数
     */
    @Test
    public void grtScore(){
        Score score = new Score();
        score.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
        IPage<Score> iPage = new Page<>();
        iPage.setCurrent(0L);
        iPage.setPages(0);
//        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq()
        IPage<Score> scoreIPage = scoreService.page(iPage , new QueryWrapper<Score>().eq("create_by" , score.getCreateBy()).orderByDesc("scoreSC"));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println(scoreIPage.getRecords().size());
        System.out.println(scoreIPage.getRecords());
    }

}
