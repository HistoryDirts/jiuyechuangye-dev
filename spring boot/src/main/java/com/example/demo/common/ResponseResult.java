package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;

    //请求成功时返回的状态码
    public static Integer CODE_SUCCESS = 200;
    //请求失败
    public static Integer CODE_FAILED = -1;


    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
