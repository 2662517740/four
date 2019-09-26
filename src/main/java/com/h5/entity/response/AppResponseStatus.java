package com.h5.entity.response;


/**
 * @ClassName: UserController
 * @Description: AppResponse状态码
 * @Author: shengtt
 * @Date: 2019/4/1
 */
public enum AppResponseStatus {

    /**
     * 操作成功
     */
    SUCCESS(0),

    /**
     * 业务异常
     */
    BIZ_ERROR(3),

    /**
     * 操作失败
     */
    ERROR(1),

    /**
     * 未登录
     */
    INVALID_TOKEN(2),
    /**
     * 未查询到数据
     */
    NOT_FOUND(4);

    int code;

    AppResponseStatus(int code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }
}
