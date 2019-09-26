package com.h5.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName: UserController
 * @Description: 系统统一返回格式
 * @Author: shengtt
 * @Date: 2019/4/1
 */
@ApiModel(description = "系统统一返回格式")
public class AppResponse<T> implements Serializable {

    /**
     * 返回编码
     */
    @ApiModelProperty("返回编码")
    private int code;

    /**
     * 消息描述
     */
    @ApiModelProperty("消息描述")
    private String msg;

    /**
     * 返回内容
     */
    @ApiModelProperty("返回内容")

    private T data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 构建操作成功返回体
     *
     * @return 结果
     */
    public static AppResponse success() {
        return builder().code(AppResponseStatus.SUCCESS).build();
    }

    /**
     * 构建操作成功返回体
     * <p>
     * * @param msg  操作说明
     *
     * @return 结果
     */
    public static AppResponse success(String msg) {
        return builder().code(AppResponseStatus.SUCCESS).msg(msg).build();
    }

    /**
     * 构建操作成功返回体
     *
     * @param msg  操作说明
     * @param data 返回结果
     * @return 结果
     */
    public static <T> AppResponse success(String msg, T data) {
        return builder().code(AppResponseStatus.SUCCESS).msg(msg).data(data).build();
    }

    /**
     * 构建业务异常返回体
     *
     * @param msg 异常描述
     * @return
     */
    public static AppResponse bizError(String msg) {
        return builder().code(AppResponseStatus.BIZ_ERROR).msg(msg).build();
    }
    /**
     * 构建没有查询到数据返回体
     *
     * @return 结果
     */
    public static AppResponse notFound() {
        return builder().code(AppResponseStatus.NOT_FOUND).build();
    }

    /**
     * 构建没有查询到数据返回体
     *
     * @param msg 结果说明
     * @return 结果
     */
    public static AppResponse notFound(String msg) {
        return builder().code(AppResponseStatus.NOT_FOUND).msg(msg).build();
    }

    /**
     * 构建未登录错误返回体
     *
     * @param msg 错误说明
     * @return 结果
     */
    public static AppResponse noPermission(String msg) {
        return builder().code(AppResponseStatus.INVALID_TOKEN).build();
    }

    /**
     * 构建数据版本变化异常返回体
     *
     * @param msg 异常描述
     * @return
     */
    public static AppResponse versionError(String msg) {
        return builder().code(AppResponseStatus.ERROR).msg(msg).build();
    }

    public AppResponse() {
    }

    private AppResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> AppResponseBuilder builder() {
        return new AppResponseBuilder<T>();
    }


    public static class AppResponseBuilder<T> {

        private int code;

        private String msg;

        private T data;

        public AppResponse build() {
            AppResponse appResponse = new AppResponse<>(this.code, this.msg, this.data);
            return appResponse;
        }

        public AppResponseBuilder code(AppResponseStatus status) {
            this.code = status.code;
            return this;
        }

        public AppResponseBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public AppResponseBuilder data(T data) {
            this.data = data;
            return this;
        }
    }
}

