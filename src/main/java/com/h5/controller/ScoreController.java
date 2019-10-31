package com.h5.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.h5.entity.Score;
import com.h5.entity.ScoreVO;
import com.h5.entity.response.AppResponse;
import com.h5.redis.RedisUtils;
import com.h5.service.IScoreService;
import com.h5.utils.UUIDUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    @Transactional(rollbackFor = Exception.class)
    public AppResponse setScore(int scoreCheckpoint , String scoreDifficulty , String scoreSC,String token){
        ScoreVO score1 = new ScoreVO();
        Score score = new Score();
        score1.setToken(token);
        score.setScoreCheckpoint(scoreCheckpoint);
        score.setScoreDifficulty(scoreDifficulty);
        score.setScoreSC(scoreSC);
        String userID = redisUtils.get(score1.getToken()+"_userID");
        score.setUserId(userID);
//        System.out.println(score);
        HashMap<String , Object> map = new HashMap<>();
        map.put("userId" , score.getUserId());
        map.put("scoreCheckpoint" , score.getScoreCheckpoint());
        map.put("scoreDifficulty",score.getScoreDifficulty());
        List<Score> list = (List<Score>) scoreService.listByMap(map);
        if (list != null && list.size()!=0){
//            System.out.println(list.get(0));
            if ( Integer.valueOf(list.get(0).getScoreSC()) < Integer.valueOf(score.getScoreSC())  ){
                score.setId(list.get(0).getId());
                score.setGmtModified(df.format(new Date()));
                score.setLastModifiedBy(list.get(0).getUserId());
                score.setVersion(list.get(0).getVersion());
                scoreService.updateById(score);
                return AppResponse.success("修改成功！");
            }else {
                return AppResponse.bizError("已存在");
            }
        }else {
            score.setId(UUIDUtil.uuidStr());
            score.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
            score.setGmtCreate(df.format(new Date()));
            score.setGmtModified(df.format(new Date()));
            score.setIsDelete(0);
            score.setLastModifiedBy("31112f5c840f42ad97e7e34a542e01b2");
            score.setVersion(0);
            score.setSortNo(0);
            boolean save = scoreService.save(score);
            if (save){
                return AppResponse.success("创建成功！");
            }
            return AppResponse.bizError("创建失败，请重试！");
        }
    }

    /**
     * 查询分数
     * @Date:2019/10/14
     * @Param:ScoreVO
     */
    @ApiOperation("查询分数")
    @GetMapping(value = "/getScore")
    @Transactional(readOnly = true)
    public IPage<Score> getScore(String UserID){
        ScoreVO score = new ScoreVO();
        String id = redisUtils.get(score.getToken());
        score.setCreateBy(id);
//        score.setCreateBy("31112f5c840f42ad97e7e34a542e01b2");
        IPage<Score> iPage = new Page<>();
        iPage.setCurrent(0L);
        iPage.setPages(0);
//        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq()
        IPage<Score> scoreIPage = scoreService.page(iPage , new QueryWrapper<Score>().eq("create_by" , score.getCreateBy()).orderByDesc("scoreSC"));
        return scoreIPage;
    }

    /**
     * 排行榜
     * @Date:2019/10/14
     * @Param:Score
     */
    @ApiOperation("排行榜")
    @GetMapping(value = "/getScoreList")
    @Transactional(readOnly = true)
    public IPage<Score> getScoreList(String scoreDifficulty){
        ScoreVO score= new ScoreVO();
        score.setScoreDifficulty(scoreDifficulty);
        IPage<Score> iPage = new Page<>();
        iPage.setPages(0);
        iPage.setCurrent(0L);
        IPage<Score> scoreIPage = scoreService.page(iPage , new QueryWrapper<Score>().eq("scoreDifficulty",score.getScoreDifficulty()).orderByDesc("scoreSC"));
        return scoreIPage;
    }
}
