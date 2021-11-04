package com.example.demo.exception;

import com.example.demo.common.ResponseResult;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionProcess {
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult<?> handler(Exception e) {
        //获取异常信息，存入返回结果中
        return new ResponseResult<>(ResponseResult.CODE_FAILED, e.getMessage(), null);
    } 
}
