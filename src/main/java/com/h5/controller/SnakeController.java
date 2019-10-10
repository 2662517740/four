package com.h5.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.h5.entity.Snake;
import com.h5.entity.SnakeVO;
import com.h5.entity.response.AppResponse;
import com.h5.redis.RedisUtils;
import com.h5.service.ISnakeService;
import com.h5.utils.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-09-23
 */
@RestController
@RequestMapping("/snake/snake")
@Api("蛇管理")
@Slf4j
@Transactional(readOnly = false)
public class SnakeController {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private ISnakeService snakeService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @Description：创建蛇
     * @Date:2019/10/10
     * @Param:SnakeVO
     */
    @ApiOperation(value = "创建蛇")
    @PostMapping(value = "setSnake")
    @Transactional(readOnly = true)
    public AppResponse setSnake(SnakeVO snake){
        String ID = redisUtils.get(snake.getToken());
        snake.setId(UUIDUtil.uuidStr());
        snake.setCreateBy(ID);
        snake.setGmtCreate(df.format(new Date()));
        snake.setGmtModified(df.format(new Date()));
        snake.setIsDelete(0);
        snake.setLastModifiedBy(ID);
        snake.setVersion(0);
        snake.setSortNo(0);
        boolean save = snakeService.save(snake);
        if (save){
            return AppResponse.success("创建成功！");
        }
        return AppResponse.bizError("创建失败，请重试!");
    }

    /**
     * @Description：修改蛇的颜色
     * @Date:2019/10/10
     * @Param:SnakeVO
     */
    @ApiOperation(value = "修改蛇的颜色")
    @PostMapping(value = "updateSnake")
    @Transactional(readOnly = true)
    public AppResponse updateSnake(SnakeVO snake){
        snake.setGmtModified(df.format(new Date()));
        snake.setLastModifiedBy("31112f5c840f42ad97e7e34a542e01b2");
        snake.setVersion(1);
        boolean update = snakeService.updateById(snake);
        if (update){
            return AppResponse.success("修改成功！");
        }
        return AppResponse.bizError("修改失败，请重试！");
    }

    /**
     * @Description：蛇列表
     * @Date:2019/10/10
     * @Param:SnakeVO
     */
    @ApiOperation(value = "蛇列表")
    @PostMapping(value = "getSnake")
    @Transactional(readOnly = true)
    public IPage<Snake> getSnake(SnakeVO snake){
        String id = redisUtils.get(snake.toString());
        snake.setCreateBy(id);
        IPage<Snake> iPage = new Page<>();
        iPage.setPages(0);
        iPage.setCurrent(0L);
        IPage<Snake> snakeIPage = snakeService.page(iPage , new QueryWrapper<Snake>().eq("create_by" ,snake.getCreateBy()));
        return snakeIPage;
    }
}

