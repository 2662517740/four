package com.h5.controller;


import com.h5.entity.ScoreVO;
import com.h5.entity.response.AppResponse;
import com.h5.redis.RedisUtils;
import com.h5.service.IScoreService;
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
 * @since 2019-09-26
 */
@Api("分数管理")
@Transactional(readOnly = false)
@Slf4j
@RestController
@RequestMapping("/score/score")
public class ScoreController {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private IScoreService scoreService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @Description：创建分数
     * @Date:2019/10/10
     * @Param:Score
     */
    @ApiOperation(value = "创建分数")
    @PostMapping(value = "setScore")
    @Transactional(readOnly = true)
    public AppResponse setScore(ScoreVO score){
//        String userName = redisUtils.get(score.getToken());
//        score.setId(UUIDUtil.uuidStr());
//        score.setCreateBy(userName);
//        score.setGmtCreate(df.format(new Date()));
//        score.setGmtModified(df.format(new Date()));
//        score.setIsDelete(0);
//        score.setLastModifiedBy(userName);
//        score.setVersion(0);
//        score.setSortNo(0);
//        boolean save = scoreService.save(score);
//        if (save){
//            return AppResponse.success("创建成功！");
//        }
        return AppResponse.bizError("创建失败！");
    }


}
