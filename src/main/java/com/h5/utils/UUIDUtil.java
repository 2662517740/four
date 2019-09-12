package com.h5.utils;


import java.util.UUID;

public class UUIDUtil {

    /**
     * @Dept：南京软件研发中心
     * @Description：生成uuid
     * @Author：shengtt
     * @Date: 2019/4/1
     * @Param：
     * @Return：java.lang.String
     */
    public static String uuidStr(){
        return  UUID.randomUUID().toString().replaceAll("-", "");
    }
}
