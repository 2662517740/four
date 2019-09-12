package com.h5.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 作用：使用redis进行缓存
 * 时间：2019/6/24
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    /**
     * 读取缓存
     */
    public String get(final String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key , value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登陆读取缓存，设置有效时间
     */
    public boolean LoginSet(final  String key , String value , int time){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key , value , time , TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public String getAndSet(final  String key , String value){
        String result = "";
        try {
            result = redisTemplate.opsForValue().getAndSet(key , value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final  String key){
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
